package io.xmeta.admin.model;


public class EntityField implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private String permanentId;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String displayName;
    private String column;
    @javax.validation.constraints.NotNull
    private EnumDataType dataType;
    private EnumInputType inputType;
    private java.util.Map<String, Object> properties;
    private boolean required;
    private boolean unique;
    private boolean searchable;
    private String description;
    private Integer position;

    public EntityField() {
    }

    public EntityField(String id, String permanentId, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, String name, String displayName, String column, EnumDataType dataType, EnumInputType inputType, java.util.Map<String, Object> properties, boolean required, boolean unique, boolean searchable, String description, Integer position) {
        this.id = id;
        this.permanentId = permanentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.displayName = displayName;
        this.column = column;
        this.dataType = dataType;
        this.inputType = inputType;
        this.properties = properties;
        this.required = required;
        this.unique = unique;
        this.searchable = searchable;
        this.description = description;
        this.position = position;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPermanentId() {
        return permanentId;
    }
    public void setPermanentId(String permanentId) {
        this.permanentId = permanentId;
    }

    public java.time.ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.time.ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.time.ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public EnumDataType getDataType() {
        return dataType;
    }
    public void setDataType(EnumDataType dataType) {
        this.dataType = dataType;
    }

    public EnumInputType getInputType() {
        return inputType;
    }
    public void setInputType(EnumInputType inputType) {
        this.inputType = inputType;
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

    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String permanentId;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private String name;
        private String displayName;
        private String column;
        private EnumDataType dataType;
        private EnumInputType inputType;
        private java.util.Map<String, Object> properties;
        private boolean required;
        private boolean unique;
        private boolean searchable;
        private String description;
        private Integer position;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setPermanentId(String permanentId) {
            this.permanentId = permanentId;
            return this;
        }

        public Builder setCreatedAt(java.time.ZonedDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(java.time.ZonedDateTime updatedAt) {
            this.updatedAt = updatedAt;
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

        public Builder setDataType(EnumDataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setInputType(EnumInputType inputType) {
            this.inputType = inputType;
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

        public Builder setPosition(Integer position) {
            this.position = position;
            return this;
        }


        public EntityField build() {
            return new EntityField(id, permanentId, createdAt, updatedAt, name, displayName, column, dataType, inputType, properties, required, unique, searchable, description, position);
        }

    }
}
