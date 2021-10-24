package io.xmeta.admin.model;


public class DeploymentCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String buildId;
    @javax.validation.constraints.NotNull
    private String environmentId;
    private String message;

    public DeploymentCreateInput() {
    }

    public DeploymentCreateInput(String buildId, String environmentId, String message) {
        this.buildId = buildId;
        this.environmentId = environmentId;
        this.message = message;
    }

    public String getBuildId() {
        return buildId;
    }
    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getEnvironmentId() {
        return environmentId;
    }
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String buildId;
        private String environmentId;
        private String message;

        public Builder() {
        }

        public Builder setBuildId(String buildId) {
            this.buildId = buildId;
            return this;
        }

        public Builder setEnvironmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        public DeploymentCreateInput build() {
            return new DeploymentCreateInput(buildId, environmentId, message);
        }

    }
}
