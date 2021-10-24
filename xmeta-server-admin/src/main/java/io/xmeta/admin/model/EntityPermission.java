package io.xmeta.admin.model;


public class EntityPermission implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private String entityVersionId;
    private EntityVersion entityVersion;
    @javax.validation.constraints.NotNull
    private EnumEntityAction action;
    @javax.validation.constraints.NotNull
    private EnumEntityPermissionType type;
    @javax.validation.constraints.NotNull
    private java.util.List<EntityPermissionRole> permissionRoles;
    @javax.validation.constraints.NotNull
    private java.util.List<EntityPermissionField> permissionFields;

    public EntityPermission() {
    }

    public EntityPermission(String id, String entityVersionId, EntityVersion entityVersion, EnumEntityAction action, EnumEntityPermissionType type, java.util.List<EntityPermissionRole> permissionRoles, java.util.List<EntityPermissionField> permissionFields) {
        this.id = id;
        this.entityVersionId = entityVersionId;
        this.entityVersion = entityVersion;
        this.action = action;
        this.type = type;
        this.permissionRoles = permissionRoles;
        this.permissionFields = permissionFields;
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

    public EntityVersion getEntityVersion() {
        return entityVersion;
    }
    public void setEntityVersion(EntityVersion entityVersion) {
        this.entityVersion = entityVersion;
    }

    public EnumEntityAction getAction() {
        return action;
    }
    public void setAction(EnumEntityAction action) {
        this.action = action;
    }

    public EnumEntityPermissionType getType() {
        return type;
    }
    public void setType(EnumEntityPermissionType type) {
        this.type = type;
    }

    public java.util.List<EntityPermissionRole> getPermissionRoles() {
        return permissionRoles;
    }
    public void setPermissionRoles(java.util.List<EntityPermissionRole> permissionRoles) {
        this.permissionRoles = permissionRoles;
    }

    public java.util.List<EntityPermissionField> getPermissionFields() {
        return permissionFields;
    }
    public void setPermissionFields(java.util.List<EntityPermissionField> permissionFields) {
        this.permissionFields = permissionFields;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String entityVersionId;
        private EntityVersion entityVersion;
        private EnumEntityAction action;
        private EnumEntityPermissionType type;
        private java.util.List<EntityPermissionRole> permissionRoles;
        private java.util.List<EntityPermissionField> permissionFields;

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

        public Builder setEntityVersion(EntityVersion entityVersion) {
            this.entityVersion = entityVersion;
            return this;
        }

        public Builder setAction(EnumEntityAction action) {
            this.action = action;
            return this;
        }

        public Builder setType(EnumEntityPermissionType type) {
            this.type = type;
            return this;
        }

        public Builder setPermissionRoles(java.util.List<EntityPermissionRole> permissionRoles) {
            this.permissionRoles = permissionRoles;
            return this;
        }

        public Builder setPermissionFields(java.util.List<EntityPermissionField> permissionFields) {
            this.permissionFields = permissionFields;
            return this;
        }


        public EntityPermission build() {
            return new EntityPermission(id, entityVersionId, entityVersion, action, type, permissionRoles, permissionFields);
        }

    }
}
