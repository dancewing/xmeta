package io.xmeta.core.service.impl;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.service.MetaDatabaseLoaderService;
import io.xmeta.core.utils.ObjectMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class JdbcMetaLoaderService implements MetaDatabaseLoaderService {

    private static final String ENTITY_COLUMN_NAMES = "id, name, displayName, table_, description";
    private static final String ENTITY_TABLE_NAME = "xmeta_entity";
    private static final String LOAD_ALL_ENTITY_SQL = "SELECT " + ENTITY_COLUMN_NAMES
            + " FROM " + ENTITY_TABLE_NAME;

    private static final String UPDATE_ENTITY_SQL = "UPDATE " + ENTITY_TABLE_NAME +
            " SET lastSyncTime=?, name=?, displayName=?, table_= ?, description= ? " +
            "WHERE id= ?";
    private static final String INSERT_ENTITY_SQL = "INSERT INTO " + ENTITY_TABLE_NAME +
            " (lastSyncTime, name, displayName, table_, description, id) " +
            "VALUES(?, ?, ?, ?, ?, ?);";

    private static final String ENTITY_FIELD_COLUMN_NAMES = "id, name, displayName, column_, dataType, javaType, required, unique_, searchable, description, properties, entity_id";
    private static final String ENTITY_FIELD_TABLE_NAME = "xmeta_entity_field";
    private static final String LOAD_ALL_ENTITY_FIELD_SQL = "SELECT " + ENTITY_FIELD_COLUMN_NAMES
            + " FROM " + ENTITY_FIELD_TABLE_NAME;

    private static final String UPDATE_ENTITY_FIELD_SQL = "UPDATE " + ENTITY_FIELD_TABLE_NAME +
            " SET name=?, displayName=?, column_=?, dataType=?, javaType=?, required=?, unique_=?, searchable=?, description=?, properties=?, entity_id=? " +
            "WHERE id=?";
    private static final String INSERT_ENTITY_FIELD_SQL = "INSERT INTO " + ENTITY_FIELD_TABLE_NAME +
            " (name, displayName, column_, dataType, javaType, required, unique_, searchable, description, properties, entity_id, id)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


    private static final String GET_LAST_SYNC_TIME = "select max(lastSyncTime) from " + ENTITY_TABLE_NAME;

    private final LobHandler lobHandler;
    private JdbcTemplate jdbcTemplate;
    protected RowMapper<Entity> entityRowMapper;
    protected RowMapper<EntityField> entityFieldRowMapper;
    protected Function<Entity, List<SqlParameterValue>> entityParametersMapper;
    protected Function<EntityField, List<SqlParameterValue>> entityFieldParametersMapper;

    public JdbcMetaLoaderService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.lobHandler = new DefaultLobHandler();
        this.entityRowMapper = new EntityRowMapper();
        this.entityFieldRowMapper = new EntityFieldRowMapper(this.lobHandler);
        this.entityParametersMapper = new EntityParametersMapper();
        this.entityFieldParametersMapper = new EntityFieldParametersMapper();
    }

    @Override
    public List<Entity> load() {
        List<Entity> entities = jdbcTemplate.query(LOAD_ALL_ENTITY_SQL, this.entityRowMapper);
        List<EntityField> entityFields = jdbcTemplate.query(LOAD_ALL_ENTITY_FIELD_SQL, this.entityFieldRowMapper);

        entityFields.forEach(entityField -> {
            Optional<Entity> optionalEntity = entities.stream().filter(entity -> StringUtils.equals(entity.getId(), entityField.getEntityId())).findFirst();
            if (optionalEntity.isPresent()) {
                Entity entity = optionalEntity.get();
                entity.getFields().add(entityField);
            } else {
                throw new RuntimeException("");
            }
        });

        return entities;
    }

    @Override
    public long getLastSyncTime() {
        Long lastSyncTime = jdbcTemplate.queryForObject(GET_LAST_SYNC_TIME, Long.class);
        if (lastSyncTime != null) {
            return lastSyncTime;
        }
        return 0;
    }

    @Override
    public void save(List<Entity> entities) {
        List<Entity> existingEntities = load();

        long currTime = System.currentTimeMillis();

        Map<String, EntityField> existingEntityFields = new HashMap<>();
        existingEntities.forEach(entity -> {
            entity.getFields().forEach(entityField -> {
                existingEntityFields.put(entityField.getId(), entityField);
            });

        });
        for (Entity entity : entities) {
            if (existingEntities.stream().anyMatch(e -> StringUtils.equals(e.getId(), entity.getId()))) {
                updateEntity(jdbcTemplate, entity, currTime);
            } else {
                saveEntity(jdbcTemplate, entity, currTime);
            }
            entity.getFields().forEach(entityField -> {
                entityField.setEntityId(entity.getId());
                if (existingEntityFields.containsKey(entityField.getId())) {
                    updateEntityField(jdbcTemplate, entityField);
                } else {
                    saveEntityField(jdbcTemplate, entityField);
                }
            });
        }
    }

    private void updateEntityField(JdbcTemplate jdbcTemplate, EntityField entityField) {
        List<SqlParameterValue> parameters = this.entityFieldParametersMapper
                .apply(entityField);
        try (LobCreator lobCreator = this.lobHandler.getLobCreator()) {
            PreparedStatementSetter pss = new LobCreatorArgumentPreparedStatementSetter(lobCreator,
                    parameters.toArray());
            jdbcTemplate.update(UPDATE_ENTITY_FIELD_SQL, pss);
        }
    }

    private void saveEntityField(JdbcTemplate jdbcTemplate, EntityField entityField) {
        List<SqlParameterValue> parameters = this.entityFieldParametersMapper
                .apply(entityField);
        try (LobCreator lobCreator = this.lobHandler.getLobCreator()) {
            PreparedStatementSetter pss = new LobCreatorArgumentPreparedStatementSetter(lobCreator,
                    parameters.toArray());
            jdbcTemplate.update(INSERT_ENTITY_FIELD_SQL, pss);
        }
    }

    private void saveEntity(JdbcTemplate jdbcTemplate, Entity entity, long currTime) {
        List<SqlParameterValue> parameters = this.entityParametersMapper
                .apply(entity);
        parameters.add(0, new SqlParameterValue(Types.BIGINT, currTime));
        try (LobCreator lobCreator = this.lobHandler.getLobCreator()) {
            PreparedStatementSetter pss = new LobCreatorArgumentPreparedStatementSetter(lobCreator,
                    parameters.toArray());
            jdbcTemplate.update(INSERT_ENTITY_SQL, pss);
        }
    }

    private void updateEntity(JdbcTemplate jdbcTemplate, Entity entity, long currTime) {
        List<SqlParameterValue> parameters = this.entityParametersMapper
                .apply(entity);
        parameters.add(0, new SqlParameterValue(Types.BIGINT, currTime));
        try (LobCreator lobCreator = this.lobHandler.getLobCreator()) {
            PreparedStatementSetter pss = new LobCreatorArgumentPreparedStatementSetter(lobCreator,
                    parameters.toArray());
            jdbcTemplate.update(UPDATE_ENTITY_SQL, pss);
        }
    }

    public static class EntityRowMapper implements RowMapper<Entity> {

        public EntityRowMapper() {
        }

        @Override
        public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entity entity = new Entity();
            entity.setId(rs.getString("id"));
            entity.setName(rs.getString("name"));
            entity.setDisplayName(rs.getString("displayName"));
            entity.setTable(rs.getString("table_"));
            entity.setDescription(rs.getString("description"));
            return entity;
        }
    }

    public static class EntityFieldRowMapper implements RowMapper<EntityField> {
        private LobHandler lobHandler;

        public EntityFieldRowMapper(LobHandler lobHandler) {
            this.lobHandler = lobHandler;
        }

        @Override
        public EntityField mapRow(ResultSet rs, int rowNum) throws SQLException {
            EntityField entityField = new EntityField();
            entityField.setId(rs.getString("id"));
            entityField.setName(rs.getString("name"));
            entityField.setDisplayName(rs.getString("displayName"));
            entityField.setColumn(rs.getString("column_"));
            entityField.setJavaType(rs.getString("javaType"));

            entityField.setRequired(rs.getBoolean("required"));
            entityField.setUnique(rs.getBoolean("unique_"));
            entityField.setSearchable(rs.getBoolean("searchable"));
            entityField.setDescription(rs.getString("description"));
            String dataType = rs.getString("dataType");
            if (dataType != null) {
                entityField.setDataType(DataType.valueOf(dataType));
            }
            entityField.setEntityId(rs.getString("entity_id"));

            byte[] properties = this.lobHandler.getBlobAsBytes(rs, "properties");

            entityField.setProperties(ObjectMapperUtils.toMap(properties));

            return entityField;
        }
    }

    public static class EntityParametersMapper
            implements Function<Entity, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(Entity entity) {
            List<SqlParameterValue> parameterValues = new ArrayList<>();
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, entity.getName()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, entity.getDisplayName()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, entity.getTable()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, entity.getDescription()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, entity.getId()));
            return parameterValues;
        }
    }

    public static class EntityFieldParametersMapper
            implements Function<EntityField, List<SqlParameterValue>> {
        @Override
        public List<SqlParameterValue> apply(EntityField field) {
            List<SqlParameterValue> parameterValues = new ArrayList<>();
            //id, name, displayName, column_, dataType, javaType, required, unique_, searchable, description, properties, entity_id
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getName()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getDisplayName()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getColumn()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getDataType()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getJavaType()));
            parameterValues.add(new SqlParameterValue(Types.BOOLEAN, field.getRequired()));
            parameterValues.add(new SqlParameterValue(Types.BOOLEAN, field.getUnique()));
            parameterValues.add(new SqlParameterValue(Types.BOOLEAN, field.getSearchable()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getDescription()));
            parameterValues.add(new SqlParameterValue(Types.BLOB, ObjectMapperUtils.toBytes(field.getProperties())));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getEntityId()));
            parameterValues.add(new SqlParameterValue(Types.VARCHAR, field.getId()));
            return parameterValues;
        }
    }

    private static final class LobCreatorArgumentPreparedStatementSetter extends ArgumentPreparedStatementSetter {

        protected final LobCreator lobCreator;

        private LobCreatorArgumentPreparedStatementSetter(LobCreator lobCreator, Object[] args) {
            super(args);
            this.lobCreator = lobCreator;
        }

        @Override
        protected void doSetValue(PreparedStatement ps, int parameterPosition, Object argValue) throws SQLException {
            if (argValue instanceof SqlParameterValue) {
                SqlParameterValue paramValue = (SqlParameterValue) argValue;
                if (paramValue.getSqlType() == Types.BLOB) {
                    if (paramValue.getValue() != null) {
                        Assert.isInstanceOf(byte[].class, paramValue.getValue(),
                                "Value of blob parameter must be byte[]");
                    }
                    byte[] valueBytes = (byte[]) paramValue.getValue();
                    this.lobCreator.setBlobAsBytes(ps, parameterPosition, valueBytes);
                    return;
                }
            }
            super.doSetValue(ps, parameterPosition, argValue);
        }

    }
}
