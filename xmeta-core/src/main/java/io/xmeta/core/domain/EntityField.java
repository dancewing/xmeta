package io.xmeta.core.domain;


public class EntityField implements java.io.Serializable {

    private String id;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String displayName;
    private String column;
    @javax.validation.constraints.NotNull
    private DataType dataType;
    private String javaType;
    private java.util.Map<String, Object> properties;
    private boolean required;
    private boolean unique;
    private boolean searchable;
    private String description;
    private String entityId;
    private boolean deleted = false;

    public EntityField() {
    }

    public EntityField(String id, String name, String displayName, String column, DataType dataType, String javaType,
                       java.util.Map<String, Object> properties, boolean required, boolean unique, boolean searchable,
                       String description, String entityId) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.column = column;
        this.dataType = dataType;
        this.javaType = javaType;
        this.properties = properties;
        this.required = required;
        this.unique = unique;
        this.searchable = searchable;
        this.description = description;
        this.entityId = entityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public java.util.Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(java.util.Map<String, Object> properties) {
        this.properties = properties;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getEntityId() {
        return entityId;
    }

    public EntityField setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public EntityField setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String displayName;
        private String column;
        private DataType dataType;
        private String javaType;
        private java.util.Map<String, Object> properties;
        private boolean required;
        private boolean unique;
        private boolean searchable;
        private String description;
        private String entityId;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder setJavaType(String javaType) {
            this.javaType = javaType;
            return this;
        }


        public Builder setProperties(java.util.Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        public Builder setRequired(boolean required) {
            this.required = required;
            return this;
        }

        public Builder setUnique(boolean unique) {
            this.unique = unique;
            return this;
        }

        public Builder setSearchable(boolean searchable) {
            this.searchable = searchable;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setEntityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public EntityField build() {
            return new EntityField(id, name, displayName, column, dataType, javaType, properties, required, unique, searchable, description, entityId);
        }
    }
}
