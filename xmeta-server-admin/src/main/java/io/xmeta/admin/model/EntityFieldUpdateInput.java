package io.xmeta.admin.model;


public class EntityFieldUpdateInput implements java.io.Serializable {

    private String name;
    private String displayName;
    private EnumDataType dataType;
    private java.util.Map<String, Object> properties;
    private Boolean required;
    private Boolean unique;
    private Boolean searchable;
    private String description;
    private EnumInputType inputType;
    private String column;
    private Integer position;

    public EntityFieldUpdateInput() {
    }

    public EntityFieldUpdateInput(String name, String displayName, EnumDataType dataType, java.util.Map<String, Object> properties, Boolean required, Boolean unique, Boolean searchable, String description, EnumInputType inputType, String column, Integer position) {
        this.name = name;
        this.displayName = displayName;
        this.dataType = dataType;
        this.properties = properties;
        this.required = required;
        this.unique = unique;
        this.searchable = searchable;
        this.description = description;
        this.inputType = inputType;
        this.column = column;
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

    public java.util.Map<String, Object> getProperties() {
        return properties;
    }
    public void setProperties(java.util.Map<String, Object> properties) {
        this.properties = properties;
    }

    public Boolean getRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getUnique() {
        return unique;
    }
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean getSearchable() {
        return searchable;
    }
    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
        private java.util.Map<String, Object> properties;
        private Boolean required;
        private Boolean unique;
        private Boolean searchable;
        private String description;
        private EnumInputType inputType;
        private String column;
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

        public Builder setProperties(java.util.Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        public Builder setRequired(Boolean required) {
            this.required = required;
            return this;
        }

        public Builder setUnique(Boolean unique) {
            this.unique = unique;
            return this;
        }

        public Builder setSearchable(Boolean searchable) {
            this.searchable = searchable;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
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

        public Builder setPosition(Integer position) {
            this.position = position;
            return this;
        }


        public EntityFieldUpdateInput build() {
            return new EntityFieldUpdateInput(name, displayName, dataType, properties, required, unique, searchable, description, inputType, column, position);
        }

    }
}
