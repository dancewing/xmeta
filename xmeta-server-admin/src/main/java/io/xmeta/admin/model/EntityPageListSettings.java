package io.xmeta.admin.model;


public class EntityPageListSettings implements java.io.Serializable, IEntityPageSettings {

    private boolean allowCreation;
    private boolean allowDeletion;
    private boolean enableSearch;
    private String navigateToPageId;

    public EntityPageListSettings() {
    }

    public EntityPageListSettings(boolean allowCreation, boolean allowDeletion, boolean enableSearch, String navigateToPageId) {
        this.allowCreation = allowCreation;
        this.allowDeletion = allowDeletion;
        this.enableSearch = enableSearch;
        this.navigateToPageId = navigateToPageId;
    }

    public boolean getAllowCreation() {
        return allowCreation;
    }
    public void setAllowCreation(boolean allowCreation) {
        this.allowCreation = allowCreation;
    }

    public boolean getAllowDeletion() {
        return allowDeletion;
    }
    public void setAllowDeletion(boolean allowDeletion) {
        this.allowDeletion = allowDeletion;
    }

    public boolean getEnableSearch() {
        return enableSearch;
    }
    public void setEnableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    public String getNavigateToPageId() {
        return navigateToPageId;
    }
    public void setNavigateToPageId(String navigateToPageId) {
        this.navigateToPageId = navigateToPageId;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean allowCreation;
        private boolean allowDeletion;
        private boolean enableSearch;
        private String navigateToPageId;

        public Builder() {
        }

        public Builder setAllowCreation(boolean allowCreation) {
            this.allowCreation = allowCreation;
            return this;
        }

        public Builder setAllowDeletion(boolean allowDeletion) {
            this.allowDeletion = allowDeletion;
            return this;
        }

        public Builder setEnableSearch(boolean enableSearch) {
            this.enableSearch = enableSearch;
            return this;
        }

        public Builder setNavigateToPageId(String navigateToPageId) {
            this.navigateToPageId = navigateToPageId;
            return this;
        }


        public EntityPageListSettings build() {
            return new EntityPageListSettings(allowCreation, allowDeletion, enableSearch, navigateToPageId);
        }

    }
}
