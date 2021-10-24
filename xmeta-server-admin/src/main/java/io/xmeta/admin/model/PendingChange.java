package io.xmeta.admin.model;


public class PendingChange implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private EnumPendingChangeAction action;
    @javax.validation.constraints.NotNull
    private EnumPendingChangeResourceType resourceType;
    @javax.validation.constraints.NotNull
    private String resourceId;
    @javax.validation.constraints.NotNull
    private PendingChangeResource resource;
    private int versionNumber;

    public PendingChange() {
    }

    public PendingChange(EnumPendingChangeAction action, EnumPendingChangeResourceType resourceType, String resourceId, PendingChangeResource resource, int versionNumber) {
        this.action = action;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.resource = resource;
        this.versionNumber = versionNumber;
    }

    public EnumPendingChangeAction getAction() {
        return action;
    }
    public void setAction(EnumPendingChangeAction action) {
        this.action = action;
    }

    public EnumPendingChangeResourceType getResourceType() {
        return resourceType;
    }
    public void setResourceType(EnumPendingChangeResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public PendingChangeResource getResource() {
        return resource;
    }
    public void setResource(PendingChangeResource resource) {
        this.resource = resource;
    }

    public int getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumPendingChangeAction action;
        private EnumPendingChangeResourceType resourceType;
        private String resourceId;
        private PendingChangeResource resource;
        private int versionNumber;

        public Builder() {
        }

        public Builder setAction(EnumPendingChangeAction action) {
            this.action = action;
            return this;
        }

        public Builder setResourceType(EnumPendingChangeResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public Builder setResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder setResource(PendingChangeResource resource) {
            this.resource = resource;
            return this;
        }

        public Builder setVersionNumber(int versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }


        public PendingChange build() {
            return new PendingChange(action, resourceType, resourceId, resource, versionNumber);
        }

    }
}
