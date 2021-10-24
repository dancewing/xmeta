package io.xmeta.admin.model;


public class EntityFieldCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private EnumDataType dataType;
    private EnumInputType inputType;
    private String column;
    @javax.validation.constraints.NotNull
    private java.util.Map<String, Object> properties;
    private boolean required;
    private boolean unique;
    private boolean searchable;
    @javax.validation.constraints.NotNull
    private String description;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput entity;
    private Integer position;

    public EntityFieldCreateInput() {
    }

    public EntityFieldCreateInput(String name, String displayName, EnumDataType dataType, EnumInputType inputType, String column, java.util.Map<String, Object> properties, boolean required, boolean unique, boolean searchable, String description, WhereParentIdInput entity, Integer position) {
        this.name = name;
        this.displayName = displayName;
        this.dataType = dataType;
        this.inputType = inputType;
        this.column = column;
        this.properties = properties;
        this.required = required;
        this.unique = unique;
        this.searchable = searchable;
        this.description = description;
        this.entity = entity;
        this.position = position;
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

    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
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

    public WhereParentIdInput getEntity() {
        return entity;
    }
    public void setEntity(WhereParentIdInput entity) {
        this.entity = entity;
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

        private String name;
        private String displayName;
        private EnumDataType dataType;
        private EnumInputType inputType;
        private String column;
        private java.util.Map<String, Object> properties;
        private boolean required;
        private boolean unique;
        private boolean searchable;
        private String description;
        private WhereParentIdInput entity;
        private Integer position;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
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

        public Builder setColumn(String column) {
            this.column = column;
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

        public Builder setEntity(WhereParentIdInput entity) {
            this.entity = entity;
            return this;
        }

        public Builder setPosition(Integer position) {
            this.position = position;
            return this;
        }


        public EntityFieldCreateInput build() {
            return new EntityFieldCreateInput(name, displayName, dataType, inputType, column, properties, required, unique, searchable, description, entity, position);
        }

    }
}
