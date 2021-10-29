package io.xmeta.core.domain;

import org.apache.commons.collections.MapUtils;

import java.util.Map;

public class Relation {
    private final Map<String, Object> properties;

    private final RelationType relationType;
    private final String relatedEntityId;
    private final String relatedFieldId;
    private final boolean dominant;


    private Relation(Map<String, Object> properties) {
        this.properties = properties;

        String rt = MapUtils.getString(this.properties, RelationTypeConstants.RELATION_TYPE);
        this.relationType = RelationType.valueOf(rt);
        this.relatedEntityId = MapUtils.getString(this.properties, RelationTypeConstants.RELATED_ENTITY_ID);
        this.relatedFieldId = MapUtils.getString(this.properties, RelationTypeConstants.RELATED_FIELD_ID);
        this.dominant = MapUtils.getBooleanValue(this.properties, RelationTypeConstants.RELATION_DOMINANT);
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public String getRelatedFieldId() {
        return relatedFieldId;
    }

    public boolean isDominant() {
        return dominant;
    }

    public static Relation of(Map<String, Object> properties) {
        return new Relation(properties);
    }
}
