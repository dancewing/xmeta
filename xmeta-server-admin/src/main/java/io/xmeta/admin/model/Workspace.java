package io.xmeta.admin.model;


public class Workspace implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private java.util.List<App> apps;
    @javax.validation.constraints.NotNull
    private java.util.List<User> users;

    public Workspace() {
    }

    public Workspace(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, String name, java.util.List<App> apps, java.util.List<User> users) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.apps = apps;
        this.users = users;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<App> getApps() {
        return apps;
    }
    public void setApps(java.util.List<App> apps) {
        this.apps = apps;
    }

    public java.util.List<User> getUsers() {
        return users;
    }
    public void setUsers(java.util.List<User> users) {
        this.users = users;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private String name;
        private java.util.List<App> apps;
        private java.util.List<User> users;

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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setApps(java.util.List<App> apps) {
            this.apps = apps;
            return this;
        }

        public Builder setUsers(java.util.List<User> users) {
            this.users = users;
            return this;
        }


        public Workspace build() {
            return new Workspace(id, createdAt, updatedAt, name, apps, users);
        }

    }
}
