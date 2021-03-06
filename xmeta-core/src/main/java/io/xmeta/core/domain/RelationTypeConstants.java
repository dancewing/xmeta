package io.xmeta.core.domain;

/***
 * 关联关系常量定义
 */
public interface RelationTypeConstants {

    String RELATION_TYPE = "relationType";

    /**
     * 关联数据模型的id
     */
    String RELATED_ENTITY_ID = "relatedEntityId";

    /**
     * 关联字段的id
     */
    String RELATED_FIELD_ID = "relatedFieldId";

    /**
     * 关联字段的id
     */
    String RELATED_FIELD = "relatedField";

    /**
     * many-to-many 谁做主导
     */
    String RELATION_DOMINANT = "dominant";

    /**
     * 关联数据模型的名称
     */
    String RELATED_ENTITY = "relatedEntity";
}
