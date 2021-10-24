package io.xmeta.admin.model;


public class AppCreateWithEntitiesFieldInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    private EnumDataType dataType;
    private java.util.Map<String, Object> properties;

    public AppCreateWithEntitiesFieldInput() {
    }

    public AppCreateWithEntitiesFieldInput(String name, EnumDataType dataType, java.util.Map<String, Object> properties) {
        this.name = name;
        this.dataType = dataType;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private EnumDataType dataType;
        private java.util.Map<String, Object> properties;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
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


        public AppCreateWithEntitiesFieldInput build() {
            return new AppCreateWithEntitiesFieldInput(name, dataType, properties);
        }

    }
}
