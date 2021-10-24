package io.xmeta.admin.model;


public class EntityPageCreateInput implements java.io.Serializable {

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
    private String entityId;
    @javax.validation.constraints.NotNull
    private EnumEntityPageType pageType;
    private EntityPageSingleRecordSettingsInput singleRecordSettings;
    private EntityPageListSettingsInput listSettings;
    private boolean showAllFields;
    @javax.validation.constraints.NotNull
    private java.util.List<String> showFieldList;

    public EntityPageCreateInput() {
    }

    public EntityPageCreateInput(String displayName, String description, WhereParentIdInput app, WhereParentIdInput parentBlock, java.util.List<BlockInputOutputInput> inputParameters, java.util.List<BlockInputOutputInput> outputParameters, String entityId, EnumEntityPageType pageType, EntityPageSingleRecordSettingsInput singleRecordSettings, EntityPageListSettingsInput listSettings, boolean showAllFields, java.util.List<String> showFieldList) {
        this.displayName = displayName;
        this.description = description;
        this.app = app;
        this.parentBlock = parentBlock;
        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
        this.entityId = entityId;
        this.pageType = pageType;
        this.singleRecordSettings = singleRecordSettings;
        this.listSettings = listSettings;
        this.showAllFields = showAllFields;
        this.showFieldList = showFieldList;
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

    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public EnumEntityPageType getPageType() {
        return pageType;
    }
    public void setPageType(EnumEntityPageType pageType) {
        this.pageType = pageType;
    }

    public EntityPageSingleRecordSettingsInput getSingleRecordSettings() {
        return singleRecordSettings;
    }
    public void setSingleRecordSettings(EntityPageSingleRecordSettingsInput singleRecordSettings) {
        this.singleRecordSettings = singleRecordSettings;
    }

    public EntityPageListSettingsInput getListSettings() {
        return listSettings;
    }
    public void setListSettings(EntityPageListSettingsInput listSettings) {
        this.listSettings = listSettings;
    }

    public boolean getShowAllFields() {
        return showAllFields;
    }
    public void setShowAllFields(boolean showAllFields) {
        this.showAllFields = showAllFields;
    }

    public java.util.List<String> getShowFieldList() {
        return showFieldList;
    }
    public void setShowFieldList(java.util.List<String> showFieldList) {
        this.showFieldList = showFieldList;
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
        private String entityId;
        private EnumEntityPageType pageType;
        private EntityPageSingleRecordSettingsInput singleRecordSettings;
        private EntityPageListSettingsInput listSettings;
        private boolean showAllFields;
        private java.util.List<String> showFieldList;

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

        public Builder setEntityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setPageType(EnumEntityPageType pageType) {
            this.pageType = pageType;
            return this;
        }

        public Builder setSingleRecordSettings(EntityPageSingleRecordSettingsInput singleRecordSettings) {
            this.singleRecordSettings = singleRecordSettings;
            return this;
        }

        public Builder setListSettings(EntityPageListSettingsInput listSettings) {
            this.listSettings = listSettings;
            return this;
        }

        public Builder setShowAllFields(boolean showAllFields) {
            this.showAllFields = showAllFields;
            return this;
        }

        public Builder setShowFieldList(java.util.List<String> showFieldList) {
            this.showFieldList = showFieldList;
            return this;
        }


        public EntityPageCreateInput build() {
            return new EntityPageCreateInput(displayName, description, app, parentBlock, inputParameters, outputParameters, entityId, pageType, singleRecordSettings, listSettings, showAllFields, showFieldList);
        }

    }
}
