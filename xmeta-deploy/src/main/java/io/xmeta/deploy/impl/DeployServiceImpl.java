package io.xmeta.deploy.impl;

import io.xmeta.core.dao.JdbcUtil;
import io.xmeta.core.dialect.MetaDialect;
import io.xmeta.core.domain.*;
import io.xmeta.core.exception.MetaException;
import io.xmeta.core.service.MetaDatabaseLoaderService;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.core.service.impl.JdbcMetaLoaderService;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.jdbc.DatabaseInfo;
import io.xmeta.core.utils.jdbc.JdbcUrlParserFactory;
import io.xmeta.core.utils.jdbc.UnKnownDatabaseInfo;
import io.xmeta.deploy.DeployService;
import io.xmeta.deploy.utils.MetaMappingGenernator;
import io.xmeta.screw.Config;
import io.xmeta.screw.SchemaLoader;
import io.xmeta.screw.model.Database;
import io.xmeta.screw.model.Table;
import io.xmeta.screw.model.TableColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class DeployServiceImpl implements DeployService {

    private final SchemaLoader schemaLoader;
    private final MetaDialect metaDialect;
    private final MetaEntityService metaEntityService;

    public DeployServiceImpl(SchemaLoader schemaLoader, MetaDialect metaDialect, MetaEntityService metaEntityService) {
        this.schemaLoader = schemaLoader;
        this.metaDialect = metaDialect;
        this.metaEntityService = metaEntityService;
    }

    /**
     * @param entities   数据模型
     * @param dataSource 数据源
     * @param settings   配置
     * @param saveMeta   数据定义到远程数据源
     * @param forceNull  强制字段使用null
     */
    @Override
    public void deploy(List<Entity> entities, DataSource dataSource, Map<String, Object> settings, boolean saveMeta, boolean forceNull) {
        if (settings == null) {
            settings = new HashMap<>();
        }
        String schema = null;
        String jdbcUrl = null;
        String catalog = null;
        try (Connection connection = dataSource.getConnection()) {

            jdbcUrl = connection.getMetaData().getURL();
            schema = connection.getSchema();
            catalog = connection.getCatalog();

            Config config = new Config();
            String databaseType = JdbcUrlParserFactory.getDatabaseType(jdbcUrl);
            DatabaseInfo databaseInfo = JdbcUrlParserFactory.getDatabaseInfo(jdbcUrl);
            if (databaseInfo.equals(UnKnownDatabaseInfo.INSTANCE)) {
                throw new MetaException("Unknown database");
            }

            config.setDb(databaseInfo.getDatabaseId());
            config.setSchema(schema);
            config.setConnection(connection);
            config.setDbType(databaseType);

            List<String> scripts = new ArrayList<>();

            try {
                Database database = this.schemaLoader.analyze(config, schema);
                Map<String, Table> tablesMap = database.getTablesMap();
                //Map<String, Entity> entityMap = this.metaEntityService.loadAsMap();
                List<Entity> deployEntities = new ArrayList<>();
                List<Table> additionalTables = new ArrayList<>();
                for (Entity entity : entities) {
                    Table table = new Table(database, catalog, schema, entity.getTable(), entity.getDescription());

                    if (!tablesMap.containsKey(entity.getTable())) {
                        additionalTables.addAll(init(database, table, entity, entities));
                        scripts.add(table.sqlCreate(this.metaDialect, catalog, schema));
                    } else {
                        if (entity.isDeleted()) {
                            deployEntities.add(entity);
                            scripts.add(table.sqlDrop(this.metaDialect, catalog, schema));
                        } else {
                            additionalTables.addAll(init(database, table, entity, entities));
                            scripts.addAll(table.sqlAlters(this.metaDialect, tablesMap.get(entity.getTable()).getColumnsMap(), catalog, schema));
                        }
                    }

                }

                //保存元数据，先要确保有对应的表结构
                if (saveMeta) {
                    additionalTables.add(MetaMappingGenernator.generateEntityTable(database));
                    additionalTables.add(MetaMappingGenernator.generateEntityField(database));
                }

                for (Table table : additionalTables) {

                    if (!tablesMap.containsKey(table.getName())) {
                        scripts.add(table.sqlCreate(this.metaDialect, catalog, schema));
                    } else {
                        scripts.addAll(table.sqlAlters(this.metaDialect, tablesMap.get(table.getName()).getColumnsMap(), catalog, schema));
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.info(StringUtils.join(scripts, "\n"));

            //执行当前数据库脚本
            runScripts(scripts, connection);
            //保存当前的元数据信息
            if (saveMeta) {
                MetaDatabaseLoaderService metaLoaderService = new JdbcMetaLoaderService(new SingleConnectionDataSource(connection, false));
                metaLoaderService.save(entities);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void runScripts(List<String> scripts, Connection connection) {

        boolean continueOnError = false;
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(continueOnError);
        populator.setSeparator(";");
        populator.setSqlScriptEncoding("utf-8");
        List<Resource> resources = new ArrayList<>(scripts.size());
        for (String script : scripts) {
            resources.add(new InputStreamResource(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8))));
        }
        for (Resource resource : resources) {
            populator.addScript(resource);
        }
        populator.populate(connection);
    }

    private List<Table> init(Database database, Table table, Entity entity, List<Entity> entities) {
        List<Table> additionalTables = new ArrayList<>();
        entity.getFields().forEach(field -> {
            TableColumn column = new TableColumn(table);

            String tmp = field.getColumn();
            column.setName(tmp == null ? null : tmp.intern());

            Map<String, Object> properties = field.getProperties();

            if (field.getDataType() == DataType.DecimalNumber) {
                column.setDecimalDigits(MapUtils.getIntValue(properties, FieldProperties.NUMBER_PRECISION));
            }

            int maxLength = MapUtils.getIntValue(properties, FieldProperties.STRING_MAX_LENGTH);

            if (field.getJavaType() != null && String.class.isAssignableFrom(EntityFieldUtils.getClass(field.getJavaType()))) {
                if (maxLength > 0 ) {
                    column.setLength(maxLength);
                } else {
                    column.setLength(255);
                }
            }

            StringBuilder buf = new StringBuilder();
            buf.append(column.getLength());
            if (column.getDecimalDigits() > 0) {
                buf.append(',');
                buf.append(column.getDecimalDigits());
            }
            column.setDetailedSize(buf.toString());

            column.setNullable(!field.getRequired());
            column.setDefaultValue("");
            column.setComments(field.getDescription());

            column.setId(field.getId());

            Pattern excludeIndirectColumns = Config.getInstance().getIndirectColumnExclusions();
            Pattern excludeColumns = Config.getInstance().getColumnExclusions();

            column.setAllExcluded(column.matches(excludeColumns));
            column.setExcluded(column.isAllExcluded() || column.matches(excludeIndirectColumns));

            if (field.getDataType() == DataType.Id) {
                // column.setTypeName(tmp == null ? "unknown" : tmp.intern());

                column.setType(JdbcUtil.sqlTypeFor(EntityFieldUtils.getClass(field.getJavaType())));
                table.getColumnsMap().put(field.getColumn(), column);
                table.getPrimaryColumns().add(column);

            } else if (field.getDataType() == DataType.Lookup) {

                RelationType relationType = EntityFieldUtils.getRelationType(field.getProperties());
                String relatedEntityId = MapUtils.getString(field.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID);
                if (relationType == null || StringUtils.isEmpty(relatedEntityId)) {
                    throw new RuntimeException("");
                }
                List<Entity> relationEntities = entities.stream().filter(entity1 -> StringUtils.equals(relatedEntityId, entity1.getId())).collect(Collectors.toList());
                if (relationEntities.size() != 1) {
                    throw new RuntimeException("");
                }
                String relatedEntityName = relationEntities.get(0).getName();
                if (relationType.equals(RelationType.ManyToOne) || relationType.equals(RelationType.OneWay) ) {
                    TableColumn manyToOneType = new TableColumn(table);
                    manyToOneType.setName(field.getColumn());
                    manyToOneType.setType(Types.VARCHAR);
                    manyToOneType.setLength(64);
                    table.getColumnsMap().put(field.getColumn(), manyToOneType);

                } else if (relationType.equals(RelationType.ManyToMany)) {

                    String relatedFieldId = MapUtils.getString(field.getProperties(), RelationTypeConstants.RELATED_FIELD_ID);
                    if (StringUtils.isEmpty(relatedFieldId)) {
                        throw new RuntimeException("");
                    }
                    List<EntityField> entityFieldList = relationEntities.get(0).getFields().stream().filter(targetField -> StringUtils.equals(targetField.getId(), relatedFieldId)).collect(Collectors.toList());
                    if (entityFieldList.size() != 1) {
                        throw new RuntimeException("");
                    }
                    boolean dominant = MapUtils.getBooleanValue(field.getProperties(), RelationTypeConstants.RELATION_DOMINANT, false);

                    //主导方负责创建表
                    if (dominant) {
                        EntityField targetEntityField = entityFieldList.get(0);
                        String joinTable = getJoinTable(entity.getTable(), relationEntities.get(0).getTable(), false);
                        Table manyToManyTable = new Table(database, table.getCatalog(), table.getSchema(),  joinTable,"");

                        TableColumn tc1 = new TableColumn(table);
                        tc1.setName(field.getColumn());
                        tc1.setType(Types.VARCHAR);
                        tc1.setLength(64);

                        TableColumn tc2 = new TableColumn(table);
                        tc2.setName(targetEntityField.getColumn());
                        tc2.setType(Types.VARCHAR);
                        tc2.setLength(64);

                        manyToManyTable.getColumnsMap().put(tc1.getName(), tc1);
                        manyToManyTable.getColumnsMap().put(tc2.getName(), tc2);

                        additionalTables.add(manyToManyTable);
                    }
                }
            } else {
                column.setType(JdbcUtil.sqlTypeFor(EntityFieldUtils.getClass(field.getJavaType())));
                table.getColumnsMap().put(field.getColumn(), column);
            }

        });

        return additionalTables;
    }

    private Map<String, Entity> map(List<Entity> entities) {
        Map<String, Entity> entityMap = new HashMap<>();
        for (Entity entity : entities) {
            entityMap.put(entity.getTable(), entity);
        }
        return entityMap;
    }

    private static String getJoinTable(String source, String target, boolean inverse) {
        String table = null;
        if (inverse) {
            table = target + "_" + source;
        } else {
            table = source + "_" + target;
        }
        table = StringUtils.replace(table, "-", "_");
        return table.toLowerCase(Locale.ROOT);
    }
}
