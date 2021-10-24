package io.xmeta.admin.model;


public class Commit implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private String userId;
    private User user;
    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private java.util.List<PendingChange> changes;

    public Commit() {
    }

    public Commit(String id, java.time.ZonedDateTime createdAt, String userId, User user, String message, java.util.List<PendingChange> changes) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.user = user;
        this.message = message;
        this.changes = changes;
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

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public java.util.List<PendingChange> getChanges() {
        return changes;
    }
    public void setChanges(java.util.List<PendingChange> changes) {
        this.changes = changes;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private String userId;
        private User user;
        private String message;
        private java.util.List<PendingChange> changes;

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

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setChanges(java.util.List<PendingChange> changes) {
            this.changes = changes;
            return this;
        }


        public Commit build() {
            return new Commit(id, createdAt, userId, user, message, changes);
        }

    }
}
