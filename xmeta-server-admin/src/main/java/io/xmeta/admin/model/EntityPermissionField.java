package io.xmeta.admin.model;


public class EntityPermissionField implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private String permissionId;
    private EntityPermission permission;
    @javax.validation.constraints.NotNull
    private String fieldPermanentId;
    @javax.validation.constraints.NotNull
    private String entityVersionId;
    @javax.validation.constraints.NotNull
    private EntityField field;
    @javax.validation.constraints.NotNull
    private java.util.List<EntityPermissionRole> permissionRoles;

    public EntityPermissionField() {
    }

    public EntityPermissionField(String id, String permissionId, EntityPermission permission, String fieldPermanentId, String entityVersionId, EntityField field, java.util.List<EntityPermissionRole> permissionRoles) {
        this.id = id;
        this.permissionId = permissionId;
        this.permission = permission;
        this.fieldPermanentId = fieldPermanentId;
        this.entityVersionId = entityVersionId;
        this.field = field;
        this.permissionRoles = permissionRoles;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public EntityPermission getPermission() {
        return permission;
    }
    public void setPermission(EntityPermission permission) {
        this.permission = permission;
    }

    public String getFieldPermanentId() {
        return fieldPermanentId;
    }
    public void setFieldPermanentId(String fieldPermanentId) {
        this.fieldPermanentId = fieldPermanentId;
    }

    public String getEntityVersionId() {
        return entityVersionId;
    }
    public void setEntityVersionId(String entityVersionId) {
        this.entityVersionId = entityVersionId;
    }

    public EntityField getField() {
        return field;
    }
    public void setField(EntityField field) {
        this.field = field;
    }

    public java.util.List<EntityPermissionRole> getPermissionRoles() {
        return permissionRoles;
    }
    public void setPermissionRoles(java.util.List<EntityPermissionRole> permissionRoles) {
        this.permissionRoles = permissionRoles;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String permissionId;
        private EntityPermission permission;
        private String fieldPermanentId;
        private String entityVersionId;
        private EntityField field;
        private java.util.List<EntityPermissionRole> permissionRoles;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setPermissionId(String permissionId) {
            this.permissionId = permissionId;
            return this;
        }

        public Builder setPermission(EntityPermission permission) {
            this.permission = permission;
            return this;
        }

        public Builder setFieldPermanentId(String fieldPermanentId) {
            this.fieldPermanentId = fieldPermanentId;
            return this;
        }

        public Builder setEntityVersionId(String entityVersionId) {
            this.entityVersionId = entityVersionId;
            return this;
        }

        public Builder setField(EntityField field) {
            this.field = field;
            return this;
        }

        public Builder setPermissionRoles(java.util.List<EntityPermissionRole> permissionRoles) {
            this.permissionRoles = permissionRoles;
            return this;
        }


        public EntityPermissionField build() {
            return new EntityPermissionField(id, permissionId, permission, fieldPermanentId, entityVersionId, field, permissionRoles);
        }

    }
}
