package io.xmeta.admin.model;


public class EntityUpdatePermissionInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private EnumEntityAction action;
    @javax.validation.constraints.NotNull
    private EnumEntityPermissionType type;

    public EntityUpdatePermissionInput() {
    }

    public EntityUpdatePermissionInput(EnumEntityAction action, EnumEntityPermissionType type) {
        this.action = action;
        this.type = type;
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



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumEntityAction action;
        private EnumEntityPermissionType type;

        public Builder() {
        }

        public Builder setAction(EnumEntityAction action) {
            this.action = action;
            return this;
        }

        public Builder setType(EnumEntityPermissionType type) {
            this.type = type;
            return this;
        }


        public EntityUpdatePermissionInput build() {
            return new EntityUpdatePermissionInput(action, type);
        }

    }
}
