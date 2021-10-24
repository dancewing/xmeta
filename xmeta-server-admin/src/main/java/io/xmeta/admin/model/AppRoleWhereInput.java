package io.xmeta.admin.model;


public class AppRoleWhereInput implements java.io.Serializable {

    private String id;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private StringFilter name;
    private StringFilter displayName;
    private StringFilter description;
    private WhereUniqueInput app;

    public AppRoleWhereInput() {
    }

    public AppRoleWhereInput(String id, DateTimeFilter createdAt, DateTimeFilter updatedAt, StringFilter name, StringFilter displayName, StringFilter description, WhereUniqueInput app) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.app = app;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public StringFilter getDescription() {
        return description;
    }
    public void setDescription(StringFilter description) {
        this.description = description;
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

        private String id;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private StringFilter name;
        private StringFilter displayName;
        private StringFilter description;
        private WhereUniqueInput app;

        public Builder() {
        }

        public Builder setId(String id) {
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

        public Builder setDescription(StringFilter description) {
            this.description = description;
            return this;
        }

        public Builder setApp(WhereUniqueInput app) {
            this.app = app;
            return this;
        }


        public AppRoleWhereInput build() {
            return new AppRoleWhereInput(id, createdAt, updatedAt, name, displayName, description, app);
        }

    }
}
