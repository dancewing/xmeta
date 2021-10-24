package io.xmeta.admin.model;


public class Build implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private App app;
    @javax.validation.constraints.NotNull
    private String appId;
    @javax.validation.constraints.NotNull
    private User createdBy;
    @javax.validation.constraints.NotNull
    private String userId;
    private EnumBuildStatus status;
    @javax.validation.constraints.NotNull
    private String archiveURI;
    @javax.validation.constraints.NotNull
    private String version;
    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private String actionId;
    private Action action;
    @javax.validation.constraints.NotNull
    private Commit commit;
    @javax.validation.constraints.NotNull
    private String commitId;

    public Build() {
    }

    public Build(String id, java.time.ZonedDateTime createdAt, App app, String appId, User createdBy, String userId, EnumBuildStatus status, String archiveURI, String version, String message, String actionId, Action action, Commit commit, String commitId) {
        this.id = id;
        this.createdAt = createdAt;
        this.app = app;
        this.appId = appId;
        this.createdBy = createdBy;
        this.userId = userId;
        this.status = status;
        this.archiveURI = archiveURI;
        this.version = version;
        this.message = message;
        this.actionId = actionId;
        this.action = action;
        this.commit = commit;
        this.commitId = commitId;
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

    public User getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EnumBuildStatus getStatus() {
        return status;
    }
    public void setStatus(EnumBuildStatus status) {
        this.status = status;
    }

    public String getArchiveURI() {
        return archiveURI;
    }
    public void setArchiveURI(String archiveURI) {
        this.archiveURI = archiveURI;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getActionId() {
        return actionId;
    }
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Action getAction() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }

    public Commit getCommit() {
        return commit;
    }
    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public String getCommitId() {
        return commitId;
    }
    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private App app;
        private String appId;
        private User createdBy;
        private String userId;
        private EnumBuildStatus status;
        private String archiveURI;
        private String version;
        private String message;
        private String actionId;
        private Action action;
        private Commit commit;
        private String commitId;

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

        public Builder setApp(App app) {
            this.app = app;
            return this;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setCreatedBy(User createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setStatus(EnumBuildStatus status) {
            this.status = status;
            return this;
        }

        public Builder setArchiveURI(String archiveURI) {
            this.archiveURI = archiveURI;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setActionId(String actionId) {
            this.actionId = actionId;
            return this;
        }

        public Builder setAction(Action action) {
            this.action = action;
            return this;
        }

        public Builder setCommit(Commit commit) {
            this.commit = commit;
            return this;
        }

        public Builder setCommitId(String commitId) {
            this.commitId = commitId;
            return this;
        }


        public Build build() {
            return new Build(id, createdAt, app, appId, createdBy, userId, status, archiveURI, version, message, actionId, action, commit, commitId);
        }

    }
}
