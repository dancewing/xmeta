package io.xmeta.admin.model;


public class Environment implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private App app;
    @javax.validation.constraints.NotNull
    private String appId;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private EnumDatabaseType database;
    private String description;
    @javax.validation.constraints.NotNull
    private String address;
    @javax.validation.constraints.NotNull
    private String user;
    private String password;

    public Environment() {
    }

    public Environment(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, App app, String appId, String name, EnumDatabaseType database, String description, String address, String user, String password) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.app = app;
        this.appId = appId;
        this.name = name;
        this.database = database;
        this.description = description;
        this.address = address;
        this.user = user;
        this.password = password;
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

    public EnumDatabaseType getDatabase() {
        return database;
    }
    public void setDatabase(EnumDatabaseType database) {
        this.database = database;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
        private EnumDatabaseType database;
        private String description;
        private String address;
        private String user;
        private String password;

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

        public Builder setDatabase(EnumDatabaseType database) {
            this.database = database;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }


        public Environment build() {
            return new Environment(id, createdAt, updatedAt, app, appId, name, database, description, address, user, password);
        }

    }
}
