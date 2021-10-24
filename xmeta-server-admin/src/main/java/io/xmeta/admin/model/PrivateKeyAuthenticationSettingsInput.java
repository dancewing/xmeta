package io.xmeta.admin.model;


public class PrivateKeyAuthenticationSettingsInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String keyName;
    @javax.validation.constraints.NotNull
    private String keyValue;
    @javax.validation.constraints.NotNull
    private String type;

    public PrivateKeyAuthenticationSettingsInput() {
    }

    public PrivateKeyAuthenticationSettingsInput(String keyName, String keyValue, String type) {
        this.keyName = keyName;
        this.keyValue = keyValue;
        this.type = type;
    }

    public String getKeyName() {
        return keyName;
    }
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String keyName;
        private String keyValue;
        private String type;

        public Builder() {
        }

        public Builder setKeyName(String keyName) {
            this.keyName = keyName;
            return this;
        }

        public Builder setKeyValue(String keyValue) {
            this.keyValue = keyValue;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }


        public PrivateKeyAuthenticationSettingsInput build() {
            return new PrivateKeyAuthenticationSettingsInput(keyName, keyValue, type);
        }

    }
}
