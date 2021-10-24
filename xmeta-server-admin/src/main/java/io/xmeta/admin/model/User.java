package io.xmeta.admin.model;


public class User implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    private Account account;
    private Workspace workspace;
    @javax.validation.constraints.NotNull
    private java.util.List<UserRole> userRoles;
    private boolean isOwner;

    public User() {
    }

    public User(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, Account account, Workspace workspace, java.util.List<UserRole> userRoles, boolean isOwner) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.account = account;
        this.workspace = workspace;
        this.userRoles = userRoles;
        this.isOwner = isOwner;
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

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Workspace getWorkspace() {
        return workspace;
    }
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public java.util.List<UserRole> getUserRoles() {
        return userRoles;
    }
    public void setUserRoles(java.util.List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean getIsOwner() {
        return isOwner;
    }
    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private Account account;
        private Workspace workspace;
        private java.util.List<UserRole> userRoles;
        private boolean isOwner;

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

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public Builder setWorkspace(Workspace workspace) {
            this.workspace = workspace;
            return this;
        }

        public Builder setUserRoles(java.util.List<UserRole> userRoles) {
            this.userRoles = userRoles;
            return this;
        }

        public Builder setIsOwner(boolean isOwner) {
            this.isOwner = isOwner;
            return this;
        }


        public User build() {
            return new User(id, createdAt, updatedAt, account, workspace, userRoles, isOwner);
        }

    }
}
