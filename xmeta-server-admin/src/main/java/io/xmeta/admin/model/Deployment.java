package io.xmeta.admin.model;


public class Deployment implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private User createdBy;
    @javax.validation.constraints.NotNull
    private String userId;
    @javax.validation.constraints.NotNull
    private Build build;
    @javax.validation.constraints.NotNull
    private String buildId;
    @javax.validation.constraints.NotNull
    private Environment environment;
    @javax.validation.constraints.NotNull
    private String environmentId;
    @javax.validation.constraints.NotNull
    private EnumDeploymentStatus status;
    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private String actionId;
    private Action action;

    public Deployment() {
    }

    public Deployment(String id, java.time.ZonedDateTime createdAt, User createdBy, String userId, Build build, String buildId, Environment environment, String environmentId, EnumDeploymentStatus status, String message, String actionId, Action action) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.userId = userId;
        this.build = build;
        this.buildId = buildId;
        this.environment = environment;
        this.environmentId = environmentId;
        this.status = status;
        this.message = message;
        this.actionId = actionId;
        this.action = action;
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

    public Build getBuild() {
        return build;
    }
    public void setBuild(Build build) {
        this.build = build;
    }

    public String getBuildId() {
        return buildId;
    }
    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public Environment getEnvironment() {
        return environment;
    }
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getEnvironmentId() {
        return environmentId;
    }
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public EnumDeploymentStatus getStatus() {
        return status;
    }
    public void setStatus(EnumDeploymentStatus status) {
        this.status = status;
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



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private User createdBy;
        private String userId;
        private Build build;
        private String buildId;
        private Environment environment;
        private String environmentId;
        private EnumDeploymentStatus status;
        private String message;
        private String actionId;
        private Action action;

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

        public Builder setCreatedBy(User createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setBuild(Build build) {
            this.build = build;
            return this;
        }

        public Builder setBuildId(String buildId) {
            this.buildId = buildId;
            return this;
        }

        public Builder setEnvironment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public Builder setEnvironmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        public Builder setStatus(EnumDeploymentStatus status) {
            this.status = status;
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


        public Deployment build() {
            return new Deployment(id, createdAt, createdBy, userId, build, buildId, environment, environmentId, status, message, actionId, action);
        }

    }
}
