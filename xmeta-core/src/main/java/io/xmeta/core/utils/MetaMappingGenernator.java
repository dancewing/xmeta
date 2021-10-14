package io.xmeta.core.utils;

import org.hibernate.boot.jaxb.hbm.spi.*;

import static io.xmeta.core.utils.EntityConverter.createTypeSpecificationType;

public final class MetaMappingGenernator {
    public static void createMetaMapping(JaxbHbmHibernateMapping hibernateMapping) {

        JaxbHbmRootEntityType entityType = new JaxbHbmRootEntityType();
        entityType.setTable("xmeta_entity");
        entityType.setEntityName("XmetaEntity");
        entityType.setComment("XmetaEntity");

        JaxbHbmSimpleIdType simpleIdType = new JaxbHbmSimpleIdType();
        simpleIdType.setLength(64);
        simpleIdType.setName("id");
        simpleIdType.setType(createTypeSpecificationType(String.class));
        entityType.setId(simpleIdType);

        JaxbHbmBasicAttributeType basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("name");
        basicAttributeType.setName("name");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("displayName");
        basicAttributeType.setName("displayName");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("pluralDisplayName");
        basicAttributeType.setName("pluralDisplayName");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("table_");
        basicAttributeType.setName("table");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("description");
        basicAttributeType.setName("description");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        hibernateMapping.getClazz().add(entityType);


        entityType = new JaxbHbmRootEntityType();
        entityType.setTable("xmeta_entity_field");
        entityType.setEntityName("XmetaEntityField");
        entityType.setComment("XmetaEntityField");

        simpleIdType = new JaxbHbmSimpleIdType();
        simpleIdType.setLength(64);
        simpleIdType.setName("id");
        simpleIdType.setType(createTypeSpecificationType(String.class));
        entityType.setId(simpleIdType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("name");
        basicAttributeType.setName("name");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("displayName");
        basicAttributeType.setName("displayName");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("column_");
        basicAttributeType.setName("column");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("dataType");
        basicAttributeType.setName("dataType");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("javaType");
        basicAttributeType.setName("javaType");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("required");
        basicAttributeType.setName("required");
        basicAttributeType.setType(createTypeSpecificationType(Boolean.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("unique_");
        basicAttributeType.setName("unique");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);


        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("searchable");
        basicAttributeType.setName("searchable");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("description");
        basicAttributeType.setName("description");
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);

        basicAttributeType = new JaxbHbmBasicAttributeType();
        basicAttributeType.setColumnAttribute("properties");
        basicAttributeType.setName("properties");
        basicAttributeType.setLength(65536);
        basicAttributeType.setType(createTypeSpecificationType(String.class));
        entityType.getAttributes().add(basicAttributeType);


        JaxbHbmManyToOneType manyToOneType = new JaxbHbmManyToOneType();
        manyToOneType.setName("entity");
        manyToOneType.setColumnAttribute("entity_id");
        manyToOneType.setFetch(JaxbHbmFetchStyleEnum.JOIN);
        manyToOneType.setEntityName("XmetaEntity");

        entityType.getAttributes().add(manyToOneType);

        hibernateMapping.getClazz().add(entityType);


    }
}
