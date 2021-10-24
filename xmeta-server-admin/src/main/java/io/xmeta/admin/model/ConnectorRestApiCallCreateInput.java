package io.xmeta.admin.model;


public class ConnectorRestApiCallCreateInput implements java.io.Serializable {

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
    private String url;

    public ConnectorRestApiCallCreateInput() {
    }

    public ConnectorRestApiCallCreateInput(String displayName, String description, WhereParentIdInput app, WhereParentIdInput parentBlock, java.util.List<BlockInputOutputInput> inputParameters, java.util.List<BlockInputOutputInput> outputParameters, String url) {
        this.displayName = displayName;
        this.description = description;
        this.app = app;
        this.parentBlock = parentBlock;
        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
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
        private String url;

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

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }


        public ConnectorRestApiCallCreateInput build() {
            return new ConnectorRestApiCallCreateInput(displayName, description, app, parentBlock, inputParameters, outputParameters, url);
        }

    }
}
