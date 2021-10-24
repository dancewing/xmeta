package io.xmeta.admin.model;


public class EntityUpdatePermissionFieldRolesInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private WhereParentIdInput permissionField;
    @javax.validation.constraints.NotNull
    private java.util.List<WhereUniqueInput> deletePermissionRoles;
    @javax.validation.constraints.NotNull
    private java.util.List<WhereUniqueInput> addPermissionRoles;

    public EntityUpdatePermissionFieldRolesInput() {
    }

    public EntityUpdatePermissionFieldRolesInput(WhereParentIdInput permissionField, java.util.List<WhereUniqueInput> deletePermissionRoles, java.util.List<WhereUniqueInput> addPermissionRoles) {
        this.permissionField = permissionField;
        this.deletePermissionRoles = deletePermissionRoles;
        this.addPermissionRoles = addPermissionRoles;
    }

    public WhereParentIdInput getPermissionField() {
        return permissionField;
    }
    public void setPermissionField(WhereParentIdInput permissionField) {
        this.permissionField = permissionField;
    }

    public java.util.List<WhereUniqueInput> getDeletePermissionRoles() {
        return deletePermissionRoles;
    }
    public void setDeletePermissionRoles(java.util.List<WhereUniqueInput> deletePermissionRoles) {
        this.deletePermissionRoles = deletePermissionRoles;
    }

    public java.util.List<WhereUniqueInput> getAddPermissionRoles() {
        return addPermissionRoles;
    }
    public void setAddPermissionRoles(java.util.List<WhereUniqueInput> addPermissionRoles) {
        this.addPermissionRoles = addPermissionRoles;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private WhereParentIdInput permissionField;
        private java.util.List<WhereUniqueInput> deletePermissionRoles;
        private java.util.List<WhereUniqueInput> addPermissionRoles;

        public Builder() {
        }

        public Builder setPermissionField(WhereParentIdInput permissionField) {
            this.permissionField = permissionField;
            return this;
        }

        public Builder setDeletePermissionRoles(java.util.List<WhereUniqueInput> deletePermissionRoles) {
            this.deletePermissionRoles = deletePermissionRoles;
            return this;
        }

        public Builder setAddPermissionRoles(java.util.List<WhereUniqueInput> addPermissionRoles) {
            this.addPermissionRoles = addPermissionRoles;
            return this;
        }


        public EntityUpdatePermissionFieldRolesInput build() {
            return new EntityUpdatePermissionFieldRolesInput(permissionField, deletePermissionRoles, addPermissionRoles);
        }

    }
}
