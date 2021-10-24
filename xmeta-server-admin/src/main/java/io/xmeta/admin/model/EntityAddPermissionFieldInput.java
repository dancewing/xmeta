package io.xmeta.admin.model;


public class EntityAddPermissionFieldInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private EnumEntityAction action;
    @javax.validation.constraints.NotNull
    private String fieldName;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput entity;

    public EntityAddPermissionFieldInput() {
    }

    public EntityAddPermissionFieldInput(EnumEntityAction action, String fieldName, WhereParentIdInput entity) {
        this.action = action;
        this.fieldName = fieldName;
        this.entity = entity;
    }

    public EnumEntityAction getAction() {
        return action;
    }
    public void setAction(EnumEntityAction action) {
        this.action = action;
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public WhereParentIdInput getEntity() {
        return entity;
    }
    public void setEntity(WhereParentIdInput entity) {
        this.entity = entity;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumEntityAction action;
        private String fieldName;
        private WhereParentIdInput entity;

        public Builder() {
        }

        public Builder setAction(EnumEntityAction action) {
            this.action = action;
            return this;
        }

        public Builder setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder setEntity(WhereParentIdInput entity) {
            this.entity = entity;
            return this;
        }


        public EntityAddPermissionFieldInput build() {
            return new EntityAddPermissionFieldInput(action, fieldName, entity);
        }

    }
}
