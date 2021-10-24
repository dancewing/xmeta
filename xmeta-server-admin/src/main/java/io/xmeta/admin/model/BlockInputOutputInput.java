package io.xmeta.admin.model;


public class BlockInputOutputInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String description;
    private EnumDataType dataType;
    private String dataTypeEntityName;
    private Boolean isList;
    private Boolean includeAllPropertiesByDefault;
    @javax.validation.constraints.NotNull
    private java.util.List<PropertySelectorInput> propertyList;

    public BlockInputOutputInput() {
    }

    public BlockInputOutputInput(String name, String description, EnumDataType dataType, String dataTypeEntityName, Boolean isList, Boolean includeAllPropertiesByDefault, java.util.List<PropertySelectorInput> propertyList) {
        this.name = name;
        this.description = description;
        this.dataType = dataType;
        this.dataTypeEntityName = dataTypeEntityName;
        this.isList = isList;
        this.includeAllPropertiesByDefault = includeAllPropertiesByDefault;
        this.propertyList = propertyList;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public EnumDataType getDataType() {
        return dataType;
    }
    public void setDataType(EnumDataType dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeEntityName() {
        return dataTypeEntityName;
    }
    public void setDataTypeEntityName(String dataTypeEntityName) {
        this.dataTypeEntityName = dataTypeEntityName;
    }

    public Boolean getIsList() {
        return isList;
    }
    public void setIsList(Boolean isList) {
        this.isList = isList;
    }

    public Boolean getIncludeAllPropertiesByDefault() {
        return includeAllPropertiesByDefault;
    }
    public void setIncludeAllPropertiesByDefault(Boolean includeAllPropertiesByDefault) {
        this.includeAllPropertiesByDefault = includeAllPropertiesByDefault;
    }

    public java.util.List<PropertySelectorInput> getPropertyList() {
        return propertyList;
    }
    public void setPropertyList(java.util.List<PropertySelectorInput> propertyList) {
        this.propertyList = propertyList;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String description;
        private EnumDataType dataType;
        private String dataTypeEntityName;
        private Boolean isList;
        private Boolean includeAllPropertiesByDefault;
        private java.util.List<PropertySelectorInput> propertyList;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDataType(EnumDataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setDataTypeEntityName(String dataTypeEntityName) {
            this.dataTypeEntityName = dataTypeEntityName;
            return this;
        }

        public Builder setIsList(Boolean isList) {
            this.isList = isList;
            return this;
        }

        public Builder setIncludeAllPropertiesByDefault(Boolean includeAllPropertiesByDefault) {
            this.includeAllPropertiesByDefault = includeAllPropertiesByDefault;
            return this;
        }

        public Builder setPropertyList(java.util.List<PropertySelectorInput> propertyList) {
            this.propertyList = propertyList;
            return this;
        }


        public BlockInputOutputInput build() {
            return new BlockInputOutputInput(name, description, dataType, dataTypeEntityName, isList, includeAllPropertiesByDefault, propertyList);
        }

    }
}
