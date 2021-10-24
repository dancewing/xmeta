package io.xmeta.admin.model;


public class EntityPermissionFieldWhereUniqueInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String entityId;
    @javax.validation.constraints.NotNull
    private EnumEntityAction action;
    @javax.validation.constraints.NotNull
    private String fieldPermanentId;

    public EntityPermissionFieldWhereUniqueInput() {
    }

    public EntityPermissionFieldWhereUniqueInput(String entityId, EnumEntityAction action, String fieldPermanentId) {
        this.entityId = entityId;
        this.action = action;
        this.fieldPermanentId = fieldPermanentId;
    }

    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public EnumEntityAction getAction() {
        return action;
    }
    public void setAction(EnumEntityAction action) {
        this.action = action;
    }

    public String getFieldPermanentId() {
        return fieldPermanentId;
    }
    public void setFieldPermanentId(String fieldPermanentId) {
        this.fieldPermanentId = fieldPermanentId;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String entityId;
        private EnumEntityAction action;
        private String fieldPermanentId;

        public Builder() {
        }

        public Builder setEntityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setAction(EnumEntityAction action) {
            this.action = action;
            return this;
        }

        public Builder setFieldPermanentId(String fieldPermanentId) {
            this.fieldPermanentId = fieldPermanentId;
            return this;
        }


        public EntityPermissionFieldWhereUniqueInput build() {
            return new EntityPermissionFieldWhereUniqueInput(entityId, action, fieldPermanentId);
        }

    }
}
