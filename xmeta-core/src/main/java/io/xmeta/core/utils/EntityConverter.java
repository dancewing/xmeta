package io.xmeta.core.utils;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
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

/**
 * 工具类，将数据模型转成可部署xml 描述文件
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

        InputStream inputStream = new ByteArrayInputStream(sw.toString().getBytes(StandardCharsets.UTF_8));
        return inputStream;
    }

    private static JaxbHbmHibernateMapping createRootMapping(List<Entity> entities) {
        JaxbHbmHibernateMapping hibernateMapping = new JaxbHbmHibernateMapping();
        hibernateMapping.getClazz().addAll(createEntityMapping(entities));
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

            processFields(entityType, entity.getFields());

            rootEntityTypes.add(entityType);
        }

        return rootEntityTypes;
    }

    private static void processFields(JaxbHbmRootEntityType rootEntityType, List<EntityField> fields) {
        for (EntityField entityField : fields) {

            if (DataType.Id.equals(entityField.getDataType())) {
                JaxbHbmSimpleIdType simpleIdType = new JaxbHbmSimpleIdType();
                simpleIdType.setLength(64);
                simpleIdType.setName(entityField.getName());
                simpleIdType.setType(createTypeSpecificationType(String.class));
                rootEntityType.setId(simpleIdType);
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

    private static JaxbHbmTypeSpecificationType createTypeSpecificationType(Class<?> typeClass) {
        JaxbHbmTypeSpecificationType specificationType = new JaxbHbmTypeSpecificationType();
        specificationType.setName(typeClass.getName());
        return specificationType;
    }
}
