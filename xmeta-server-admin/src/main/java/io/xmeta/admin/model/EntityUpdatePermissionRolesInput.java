package io.xmeta.admin.model;


public class EntityUpdatePermissionRolesInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private EnumEntityAction action;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput entity;
    @javax.validation.constraints.NotNull
    private java.util.List<WhereUniqueInput> deleteRoles;
    @javax.validation.constraints.NotNull
    private java.util.List<WhereUniqueInput> addRoles;

    public EntityUpdatePermissionRolesInput() {
    }

    public EntityUpdatePermissionRolesInput(EnumEntityAction action, WhereParentIdInput entity, java.util.List<WhereUniqueInput> deleteRoles, java.util.List<WhereUniqueInput> addRoles) {
        this.action = action;
        this.entity = entity;
        this.deleteRoles = deleteRoles;
        this.addRoles = addRoles;
    }

    public EnumEntityAction getAction() {
        return action;
    }
    public void setAction(EnumEntityAction action) {
        this.action = action;
    }

    public WhereParentIdInput getEntity() {
        return entity;
    }
    public void setEntity(WhereParentIdInput entity) {
        this.entity = entity;
    }

    public java.util.List<WhereUniqueInput> getDeleteRoles() {
        return deleteRoles;
    }
    public void setDeleteRoles(java.util.List<WhereUniqueInput> deleteRoles) {
        this.deleteRoles = deleteRoles;
    }

    public java.util.List<WhereUniqueInput> getAddRoles() {
        return addRoles;
    }
    public void setAddRoles(java.util.List<WhereUniqueInput> addRoles) {
        this.addRoles = addRoles;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumEntityAction action;
        private WhereParentIdInput entity;
        private java.util.List<WhereUniqueInput> deleteRoles;
        private java.util.List<WhereUniqueInput> addRoles;

        public Builder() {
        }

        public Builder setAction(EnumEntityAction action) {
            this.action = action;
            return this;
        }

        public Builder setEntity(WhereParentIdInput entity) {
            this.entity = entity;
            return this;
        }

        public Builder setDeleteRoles(java.util.List<WhereUniqueInput> deleteRoles) {
            this.deleteRoles = deleteRoles;
            return this;
        }

        public Builder setAddRoles(java.util.List<WhereUniqueInput> addRoles) {
            this.addRoles = addRoles;
            return this;
        }


        public EntityUpdatePermissionRolesInput build() {
            return new EntityUpdatePermissionRolesInput(action, entity, deleteRoles, addRoles);
        }

    }
}
