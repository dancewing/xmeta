package io.xmeta.admin.model;


public class EntityPermissionRole implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private String entityVersionId;
    @javax.validation.constraints.NotNull
    private EnumEntityAction action;
    private EntityPermission entityPermission;
    @javax.validation.constraints.NotNull
    private String appRoleId;
    @javax.validation.constraints.NotNull
    private AppRole appRole;

    public EntityPermissionRole() {
    }

    public EntityPermissionRole(String id, String entityVersionId, EnumEntityAction action, EntityPermission entityPermission, String appRoleId, AppRole appRole) {
        this.id = id;
        this.entityVersionId = entityVersionId;
        this.action = action;
        this.entityPermission = entityPermission;
        this.appRoleId = appRoleId;
        this.appRole = appRole;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getEntityVersionId() {
        return entityVersionId;
    }
    public void setEntityVersionId(String entityVersionId) {
        this.entityVersionId = entityVersionId;
    }

    public EnumEntityAction getAction() {
        return action;
    }
    public void setAction(EnumEntityAction action) {
        this.action = action;
    }

    public EntityPermission getEntityPermission() {
        return entityPermission;
    }
    public void setEntityPermission(EntityPermission entityPermission) {
        this.entityPermission = entityPermission;
    }

    public String getAppRoleId() {
        return appRoleId;
    }
    public void setAppRoleId(String appRoleId) {
        this.appRoleId = appRoleId;
    }

    public AppRole getAppRole() {
        return appRole;
    }
    public void setAppRole(AppRole appRole) {
        this.appRole = appRole;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String entityVersionId;
        private EnumEntityAction action;
        private EntityPermission entityPermission;
        private String appRoleId;
        private AppRole appRole;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setEntityVersionId(String entityVersionId) {
            this.entityVersionId = entityVersionId;
            return this;
        }

        public Builder setAction(EnumEntityAction action) {
            this.action = action;
            return this;
        }

        public Builder setEntityPermission(EntityPermission entityPermission) {
            this.entityPermission = entityPermission;
            return this;
        }

        public Builder setAppRoleId(String appRoleId) {
            this.appRoleId = appRoleId;
            return this;
        }

        public Builder setAppRole(AppRole appRole) {
            this.appRole = appRole;
            return this;
        }


        public EntityPermissionRole build() {
            return new EntityPermissionRole(id, entityVersionId, action, entityPermission, appRoleId, appRole);
        }

    }
}
