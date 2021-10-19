package io.xmeta.deploy.utils;

import io.xmeta.core.domain.*;
import io.xmeta.core.utils.EntityFieldUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.jaxb.hbm.spi.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 工具类，将数据模型转成可部署xml 描述文件
 *
 * @author jeff_qian
 */
public final class EntityConverter {

    public static InputStream createMappingStream(List<Entity> entities) throws Exception {
        // create an instance of `JAXBContext`
        JAXBContext context = JAXBContext.newInstance(JaxbHbmHibernateMapping.class);
        // create an instance of `Marshaller`
        Marshaller marshaller = context.createMarshaller();

        JaxbHbmHibernateMapping mappings = createRootMapping(entities);

        // enable pretty-print XML output
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // write XML to `StringWriter`
        StringWriter sw = new StringWriter();
        // convert book object to XML
        marshaller.marshal(mappings, sw);

        System.out.println(sw.toString());

        return new ByteArrayInputStream(sw.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static JaxbHbmHibernateMapping createRootMapping(List<Entity> entities) {
        JaxbHbmHibernateMapping hibernateMapping = new JaxbHbmHibernateMapping();
        hibernateMapping.getClazz().addAll(createEntityMapping(entities));
        MetaMappingGenernator.createMetaMapping(hibernateMapping);
        return hibernateMapping;
    }


    private static List<JaxbHbmRootEntityType> createEntityMapping(List<Entity> entities) {
        List<JaxbHbmRootEntityType> rootEntityTypes = new ArrayList<>();
        for (Entity entity : entities) {
            JaxbHbmRootEntityType entityType = new JaxbHbmRootEntityType();
            //entityType.setMutable(!entity.isImmutable());
            String table = entity.getTable();
            if (StringUtils.isEmpty(table)) {
                table = entity.getName();
            }
            entityType.setTable(table);
            entityType.setEntityName(entity.getName());
            entityType.setComment(entity.getDescription());

            //有field 的数据才处理
            if (entity.getFields() != null && entity.getFields().size() > 0) {
                processFields(entityType, entity.getFields(), entities);
                rootEntityTypes.add(entityType);
            }
        }

        return rootEntityTypes;
    }

    private static void processFields(JaxbHbmRootEntityType rootEntityType, List<EntityField> fields, List<Entity> entities) {
        for (EntityField entityField : fields) {

            if (DataType.Id.equals(entityField.getDataType())) {
                JaxbHbmSimpleIdType simpleIdType = new JaxbHbmSimpleIdType();
                simpleIdType.setLength(64);
                simpleIdType.setName(entityField.getName());
                simpleIdType.setType(createTypeSpecificationType(String.class));
                rootEntityType.setId(simpleIdType);
            } else if (DataType.Lookup.equals(entityField.getDataType())) {
                RelationType relationType = EntityFieldUtils.getRelationType(entityField.getProperties());
                String relatedEntityId = MapUtils.getString(entityField.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID);
                if (relationType == null || StringUtils.isEmpty(relatedEntityId)) {
                    throw new RuntimeException("");
                }
                List<Entity> relationEntities = entities.stream().filter(entity -> StringUtils.equals(relatedEntityId, entity.getId())).collect(Collectors.toList());
                if (relationEntities.size() != 1) {
                    throw new RuntimeException("");
                }
                String relatedEntityName = relationEntities.get(0).getName();
                if (relationType.equals(RelationType.manyToOne)) {
                    JaxbHbmManyToOneType manyToOneType = new JaxbHbmManyToOneType();
                    manyToOneType.setName(entityField.getName());
                    manyToOneType.setColumnAttribute(entityField.getColumn());
                    manyToOneType.setFetch(JaxbHbmFetchStyleEnum.JOIN);
                    manyToOneType.setEntityName(relatedEntityName);
                    rootEntityType.getAttributes().add(manyToOneType);
                } else if (relationType.equals(RelationType.manyToMany)) {

                    String relatedFieldId = MapUtils.getString(entityField.getProperties(), RelationTypeConstants.RELATED_FIELD_ID);
                    if (StringUtils.isEmpty(relatedFieldId)) {
                        throw new RuntimeException("");
                    }
                    List<EntityField> entityFieldList = relationEntities.get(0).getFields().stream().filter(targetField -> StringUtils.equals(targetField.getId(), relatedFieldId)).collect(Collectors.toList());
                    if (entityFieldList.size() != 1) {
                        throw new RuntimeException("");
                    }
                    boolean dominant = MapUtils.getBooleanValue(entityField.getProperties(), RelationTypeConstants.RELATION_DOMINANT, false);

                    //主导方负责创建表
                    if (dominant) {
                        EntityField targetEntityField = entityFieldList.get(0);

                        JaxbHbmSetType setType = new JaxbHbmSetType();
                        setType.setInverse(!dominant);
                        setType.setName(entityField.getName());
                        setType.setTable(getJoinTable(rootEntityType.getTable(), relationEntities.get(0).getTable(), setType.isInverse()));
                        setType.setLazy(JaxbHbmLazyWithExtraEnum.TRUE);
                        setType.setFetch(JaxbHbmFetchStyleWithSubselectEnum.SELECT);

                        JaxbHbmKeyType keyType = new JaxbHbmKeyType();
                        keyType.setNotNull(Boolean.TRUE);
                        keyType.setColumnAttribute(entityField.getColumn());
                        setType.setKey(keyType);

                        JaxbHbmManyToManyCollectionElementType manyToManyCollectionElementType = new JaxbHbmManyToManyCollectionElementType();
                        manyToManyCollectionElementType.setEntityName(relatedEntityName);
                        manyToManyCollectionElementType.setColumnAttribute(targetEntityField.getColumn());

                        setType.setManyToMany(manyToManyCollectionElementType);

                        rootEntityType.getAttributes().add(setType);
                    }
                }

            } else {

                // 基础字段
                JaxbHbmBasicAttributeType basicAttributeType = new JaxbHbmBasicAttributeType();
                basicAttributeType.setColumnAttribute(entityField.getColumn());
                basicAttributeType.setName(entityField.getName());
                basicAttributeType.setType(createTypeSpecificationType(entityField.getJavaType()));
//                if (DataType.SingleLineText.equals(propertyDTO.getPropertyType() == JavaPropertyType.STRING) {
//                    if (StringUtils.equals(propertyDTO.getType(), ContentType.FIELD_TYPE_RICH_TEXT)) {
//                        //varchar最大长度65535，超过后转成longtext
//                        basicAttributeType.setLength(65536);
//                    } else {
//                        basicAttributeType.setLength(propertyDTO.getMaxLength());
//                    }
//                }
                rootEntityType.getAttributes().add(basicAttributeType);
            }
        }
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

    private static JaxbHbmTypeSpecificationType createTypeSpecificationType(String typeName) {
        if (typeName == null) {
            typeName = "java.lang.String";
        }
        Class cls = null;
        try {
            cls = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return createTypeSpecificationType(cls);
    }

    public static JaxbHbmTypeSpecificationType createTypeSpecificationType(Class<?> typeClass) {
        JaxbHbmTypeSpecificationType specificationType = new JaxbHbmTypeSpecificationType();
        specificationType.setName(typeClass.getName());
        return specificationType;
    }

}
