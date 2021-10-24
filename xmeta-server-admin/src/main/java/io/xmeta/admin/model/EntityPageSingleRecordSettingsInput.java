package io.xmeta.admin.model;


public class EntityPageSingleRecordSettingsInput implements java.io.Serializable {

    private boolean allowCreation;
    private boolean allowDeletion;
    private boolean allowUpdate;

    public EntityPageSingleRecordSettingsInput() {
    }

    public EntityPageSingleRecordSettingsInput(boolean allowCreation, boolean allowDeletion, boolean allowUpdate) {
        this.allowCreation = allowCreation;
        this.allowDeletion = allowDeletion;
        this.allowUpdate = allowUpdate;
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

    public boolean getAllowUpdate() {
        return allowUpdate;
    }
    public void setAllowUpdate(boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean allowCreation;
        private boolean allowDeletion;
        private boolean allowUpdate;

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

        public Builder setAllowUpdate(boolean allowUpdate) {
            this.allowUpdate = allowUpdate;
            return this;
        }


        public EntityPageSingleRecordSettingsInput build() {
            return new EntityPageSingleRecordSettingsInput(allowCreation, allowDeletion, allowUpdate);
        }

    }
}
