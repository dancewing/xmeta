package io.xmeta.admin.model;


public class EntityWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private StringFilter name;
    private StringFilter displayName;
    private StringFilter pluralDisplayName;
    private StringFilter description;
    private EntityFieldFilter fields;
    private WhereUniqueInput app;

    public EntityWhereInput() {
    }

    public EntityWhereInput(StringFilter id, DateTimeFilter createdAt, DateTimeFilter updatedAt, StringFilter name, StringFilter displayName, StringFilter pluralDisplayName, StringFilter description, EntityFieldFilter fields, WhereUniqueInput app) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.description = description;
        this.fields = fields;
        this.app = app;
    }

    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }

    public DateTimeFilter getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(DateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }

    public DateTimeFilter getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(DateTimeFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StringFilter getName() {
        return name;
    }
    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }
    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public StringFilter getPluralDisplayName() {
        return pluralDisplayName;
    }
    public void setPluralDisplayName(StringFilter pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public StringFilter getDescription() {
        return description;
    }
    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public EntityFieldFilter getFields() {
        return fields;
    }
    public void setFields(EntityFieldFilter fields) {
        this.fields = fields;
    }

    public WhereUniqueInput getApp() {
        return app;
    }
    public void setApp(WhereUniqueInput app) {
        this.app = app;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private StringFilter name;
        private StringFilter displayName;
        private StringFilter pluralDisplayName;
        private StringFilter description;
        private EntityFieldFilter fields;
        private WhereUniqueInput app;

        public Builder() {
        }

        public Builder setId(StringFilter id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(DateTimeFilter createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(DateTimeFilter updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setName(StringFilter name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(StringFilter displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setPluralDisplayName(StringFilter pluralDisplayName) {
            this.pluralDisplayName = pluralDisplayName;
            return this;
        }

        public Builder setDescription(StringFilter description) {
            this.description = description;
            return this;
        }

        public Builder setFields(EntityFieldFilter fields) {
            this.fields = fields;
            return this;
        }

        public Builder setApp(WhereUniqueInput app) {
            this.app = app;
            return this;
        }


        public EntityWhereInput build() {
            return new EntityWhereInput(id, createdAt, updatedAt, name, displayName, pluralDisplayName, description, fields, app);
        }

    }
}
