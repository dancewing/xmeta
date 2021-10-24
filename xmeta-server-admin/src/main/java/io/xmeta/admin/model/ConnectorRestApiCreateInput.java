package io.xmeta.admin.model;


public class ConnectorRestApiCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String displayName;
    private String description;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput app;
    private WhereParentIdInput parentBlock;
    @javax.validation.constraints.NotNull
    private java.util.List<BlockInputOutputInput> inputParameters;
    @javax.validation.constraints.NotNull
    private java.util.List<BlockInputOutputInput> outputParameters;
    @javax.validation.constraints.NotNull
    private EnumConnectorRestApiAuthenticationType authenticationType;
    private PrivateKeyAuthenticationSettingsInput privateKeyAuthenticationSettings;
    private HttpBasicAuthenticationSettingsInput httpBasicAuthenticationSettings;

    public ConnectorRestApiCreateInput() {
    }

    public ConnectorRestApiCreateInput(String displayName, String description, WhereParentIdInput app, WhereParentIdInput parentBlock, java.util.List<BlockInputOutputInput> inputParameters, java.util.List<BlockInputOutputInput> outputParameters, EnumConnectorRestApiAuthenticationType authenticationType, PrivateKeyAuthenticationSettingsInput privateKeyAuthenticationSettings, HttpBasicAuthenticationSettingsInput httpBasicAuthenticationSettings) {
        this.displayName = displayName;
        this.description = description;
        this.app = app;
        this.parentBlock = parentBlock;
        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
        this.authenticationType = authenticationType;
        this.privateKeyAuthenticationSettings = privateKeyAuthenticationSettings;
        this.httpBasicAuthenticationSettings = httpBasicAuthenticationSettings;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public WhereParentIdInput getApp() {
        return app;
    }
    public void setApp(WhereParentIdInput app) {
        this.app = app;
    }

    public WhereParentIdInput getParentBlock() {
        return parentBlock;
    }
    public void setParentBlock(WhereParentIdInput parentBlock) {
        this.parentBlock = parentBlock;
    }

    public java.util.List<BlockInputOutputInput> getInputParameters() {
        return inputParameters;
    }
    public void setInputParameters(java.util.List<BlockInputOutputInput> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public java.util.List<BlockInputOutputInput> getOutputParameters() {
        return outputParameters;
    }
    public void setOutputParameters(java.util.List<BlockInputOutputInput> outputParameters) {
        this.outputParameters = outputParameters;
    }

    public EnumConnectorRestApiAuthenticationType getAuthenticationType() {
        return authenticationType;
    }
    public void setAuthenticationType(EnumConnectorRestApiAuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public PrivateKeyAuthenticationSettingsInput getPrivateKeyAuthenticationSettings() {
        return privateKeyAuthenticationSettings;
    }
    public void setPrivateKeyAuthenticationSettings(PrivateKeyAuthenticationSettingsInput privateKeyAuthenticationSettings) {
        this.privateKeyAuthenticationSettings = privateKeyAuthenticationSettings;
    }

    public HttpBasicAuthenticationSettingsInput getHttpBasicAuthenticationSettings() {
        return httpBasicAuthenticationSettings;
    }
    public void setHttpBasicAuthenticationSettings(HttpBasicAuthenticationSettingsInput httpBasicAuthenticationSettings) {
        this.httpBasicAuthenticationSettings = httpBasicAuthenticationSettings;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String displayName;
        private String description;
        private WhereParentIdInput app;
        private WhereParentIdInput parentBlock;
        private java.util.List<BlockInputOutputInput> inputParameters;
        private java.util.List<BlockInputOutputInput> outputParameters;
        private EnumConnectorRestApiAuthenticationType authenticationType;
        private PrivateKeyAuthenticationSettingsInput privateKeyAuthenticationSettings;
        private HttpBasicAuthenticationSettingsInput httpBasicAuthenticationSettings;

        public Builder() {
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setApp(WhereParentIdInput app) {
            this.app = app;
            return this;
        }

        public Builder setParentBlock(WhereParentIdInput parentBlock) {
            this.parentBlock = parentBlock;
            return this;
        }

        public Builder setInputParameters(java.util.List<BlockInputOutputInput> inputParameters) {
            this.inputParameters = inputParameters;
            return this;
        }

        public Builder setOutputParameters(java.util.List<BlockInputOutputInput> outputParameters) {
            this.outputParameters = outputParameters;
            return this;
        }

        public Builder setAuthenticationType(EnumConnectorRestApiAuthenticationType authenticationType) {
            this.authenticationType = authenticationType;
            return this;
        }

        public Builder setPrivateKeyAuthenticationSettings(PrivateKeyAuthenticationSettingsInput privateKeyAuthenticationSettings) {
            this.privateKeyAuthenticationSettings = privateKeyAuthenticationSettings;
            return this;
        }

        public Builder setHttpBasicAuthenticationSettings(HttpBasicAuthenticationSettingsInput httpBasicAuthenticationSettings) {
            this.httpBasicAuthenticationSettings = httpBasicAuthenticationSettings;
            return this;
        }


        public ConnectorRestApiCreateInput build() {
            return new ConnectorRestApiCreateInput(displayName, description, app, parentBlock, inputParameters, outputParameters, authenticationType, privateKeyAuthenticationSettings, httpBasicAuthenticationSettings);
        }

    }
}
