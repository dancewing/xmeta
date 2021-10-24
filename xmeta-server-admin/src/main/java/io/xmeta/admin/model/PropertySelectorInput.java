package io.xmeta.admin.model;


public class PropertySelectorInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String propertyName;
    private boolean include;

    public PropertySelectorInput() {
    }

    public PropertySelectorInput(String propertyName, boolean include) {
        this.propertyName = propertyName;
        this.include = include;
    }

    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean getInclude() {
        return include;
    }
    public void setInclude(boolean include) {
        this.include = include;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String propertyName;
        private boolean include;

        public Builder() {
        }

        public Builder setPropertyName(String propertyName) {
            this.propertyName = propertyName;
            return this;
        }

        public Builder setInclude(boolean include) {
            this.include = include;
            return this;
        }


        public PropertySelectorInput build() {
            return new PropertySelectorInput(propertyName, include);
        }

    }
}
