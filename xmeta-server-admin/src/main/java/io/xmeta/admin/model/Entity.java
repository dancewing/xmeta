package io.xmeta.admin.model;


public class Entity implements java.io.Serializable, PendingChangeResource {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    private App app;
    @javax.validation.constraints.NotNull
    private String appId;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private String pluralDisplayName;
    private String table;
    private String description;
    @javax.validation.constraints.NotNull
    private java.util.List<EntityPermission> permissions;
    private String lockedByUserId;
    private User lockedByUser;
    private java.time.ZonedDateTime lockedAt;

    public Entity() {
    }

    public Entity(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, App app, String appId, String name, String displayName, String pluralDisplayName, String table, String description, java.util.List<EntityPermission> permissions, String lockedByUserId, User lockedByUser, java.time.ZonedDateTime lockedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.app = app;
        this.appId = appId;
        this.name = name;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.table = table;
        this.description = description;
        this.permissions = permissions;
        this.lockedByUserId = lockedByUserId;
        this.lockedByUser = lockedByUser;
        this.lockedAt = lockedAt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public java.time.ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.time.ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.time.ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public App getApp() {
        return app;
    }
    public void setApp(App app) {
        this.app = app;
    }

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPluralDisplayName() {
        return pluralDisplayName;
    }
    public void setPluralDisplayName(String pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.List<EntityPermission> getPermissions() {
        return permissions;
    }
    public void setPermissions(java.util.List<EntityPermission> permissions) {
        this.permissions = permissions;
    }

    public String getLockedByUserId() {
        return lockedByUserId;
    }
    public void setLockedByUserId(String lockedByUserId) {
        this.lockedByUserId = lockedByUserId;
    }

    public User getLockedByUser() {
        return lockedByUser;
    }
    public void setLockedByUser(User lockedByUser) {
        this.lockedByUser = lockedByUser;
    }

    public java.time.ZonedDateTime getLockedAt() {
        return lockedAt;
    }
    public void setLockedAt(java.time.ZonedDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private App app;
        private String appId;
        private String name;
        private String displayName;
        private String pluralDisplayName;
        private String table;
        private String description;
        private java.util.List<EntityPermission> permissions;
        private String lockedByUserId;
        private User lockedByUser;
        private java.time.ZonedDateTime lockedAt;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(java.time.ZonedDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(java.time.ZonedDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setApp(App app) {
            this.app = app;
            return this;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setPluralDisplayName(String pluralDisplayName) {
            this.pluralDisplayName = pluralDisplayName;
            return this;
        }

        public Builder setTable(String table) {
            this.table = table;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPermissions(java.util.List<EntityPermission> permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder setLockedByUserId(String lockedByUserId) {
            this.lockedByUserId = lockedByUserId;
            return this;
        }

        public Builder setLockedByUser(User lockedByUser) {
            this.lockedByUser = lockedByUser;
            return this;
        }

        public Builder setLockedAt(java.time.ZonedDateTime lockedAt) {
            this.lockedAt = lockedAt;
            return this;
        }


        public Entity build() {
            return new Entity(id, createdAt, updatedAt, app, appId, name, displayName, pluralDisplayName, table, description, permissions, lockedByUserId, lockedByUser, lockedAt);
        }

    }
}
