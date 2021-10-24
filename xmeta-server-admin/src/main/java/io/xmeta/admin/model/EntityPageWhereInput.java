package io.xmeta.admin.model;


public class EntityPageWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private WhereUniqueInput app;
    private WhereUniqueInput parentBlock;
    private StringFilter displayName;
    private StringFilter description;

    public EntityPageWhereInput() {
    }

    public EntityPageWhereInput(StringFilter id, DateTimeFilter createdAt, DateTimeFilter updatedAt, WhereUniqueInput app, WhereUniqueInput parentBlock, StringFilter displayName, StringFilter description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.app = app;
        this.parentBlock = parentBlock;
        this.displayName = displayName;
        this.description = description;
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

    public WhereUniqueInput getApp() {
        return app;
    }
    public void setApp(WhereUniqueInput app) {
        this.app = app;
    }

    public WhereUniqueInput getParentBlock() {
        return parentBlock;
    }
    public void setParentBlock(WhereUniqueInput parentBlock) {
        this.parentBlock = parentBlock;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }
    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public StringFilter getDescription() {
        return description;
    }
    public void setDescription(StringFilter description) {
        this.description = description;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private WhereUniqueInput app;
        private WhereUniqueInput parentBlock;
        private StringFilter displayName;
        private StringFilter description;

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

        public Builder setApp(WhereUniqueInput app) {
            this.app = app;
            return this;
        }

        public Builder setParentBlock(WhereUniqueInput parentBlock) {
            this.parentBlock = parentBlock;
            return this;
        }

        public Builder setDisplayName(StringFilter displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(StringFilter description) {
            this.description = description;
            return this;
        }


        public EntityPageWhereInput build() {
            return new EntityPageWhereInput(id, createdAt, updatedAt, app, parentBlock, displayName, description);
        }

    }
}
