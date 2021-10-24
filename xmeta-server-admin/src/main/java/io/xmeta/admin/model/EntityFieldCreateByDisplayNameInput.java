package io.xmeta.admin.model;


public class EntityFieldCreateByDisplayNameInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String displayName;
    private EnumDataType dataType;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput entity;
    private java.util.Map<String, Object> properties;

    public EntityFieldCreateByDisplayNameInput() {
    }

    public EntityFieldCreateByDisplayNameInput(String displayName, EnumDataType dataType, WhereParentIdInput entity, java.util.Map<String, Object> properties) {
        this.displayName = displayName;
        this.dataType = dataType;
        this.entity = entity;
        this.properties = properties;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public EnumDataType getDataType() {
        return dataType;
    }
    public void setDataType(EnumDataType dataType) {
        this.dataType = dataType;
    }

    public WhereParentIdInput getEntity() {
        return entity;
    }
    public void setEntity(WhereParentIdInput entity) {
        this.entity = entity;
    }

    public java.util.Map<String, Object> getProperties() {
        return properties;
    }
    public void setProperties(java.util.Map<String, Object> properties) {
        this.properties = properties;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String displayName;
        private EnumDataType dataType;
        private WhereParentIdInput entity;
        private java.util.Map<String, Object> properties;

        public Builder() {
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDataType(EnumDataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setEntity(WhereParentIdInput entity) {
            this.entity = entity;
            return this;
        }

        public Builder setProperties(java.util.Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }


        public EntityFieldCreateByDisplayNameInput build() {
            return new EntityFieldCreateByDisplayNameInput(displayName, dataType, entity, properties);
        }

    }
}
