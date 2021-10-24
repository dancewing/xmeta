package io.xmeta.admin.model;


public class EntityPageUpdateInput implements java.io.Serializable {

    private String displayName;
    private String description;
    private String entityId;
    @javax.validation.constraints.NotNull
    private EnumEntityPageType pageType;
    private EntityPageSingleRecordSettingsInput singleRecordSettings;
    private EntityPageListSettingsInput listSettings;
    private boolean showAllFields;
    @javax.validation.constraints.NotNull
    private java.util.List<String> showFieldList;

    public EntityPageUpdateInput() {
    }

    public EntityPageUpdateInput(String displayName, String description, String entityId, EnumEntityPageType pageType, EntityPageSingleRecordSettingsInput singleRecordSettings, EntityPageListSettingsInput listSettings, boolean showAllFields, java.util.List<String> showFieldList) {
        this.displayName = displayName;
        this.description = description;
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


        public EntityPageUpdateInput build() {
            return new EntityPageUpdateInput(displayName, description, entityId, pageType, singleRecordSettings, listSettings, showAllFields, showFieldList);
        }

    }
}
