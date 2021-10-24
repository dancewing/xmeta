package io.xmeta.admin.model;


public class DeploymentOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder createdAt;
    private SortOrder userId;
    private SortOrder status;
    private SortOrder message;

    public DeploymentOrderByInput() {
    }

    public DeploymentOrderByInput(SortOrder id, SortOrder createdAt, SortOrder userId, SortOrder status, SortOrder message) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.status = status;
        this.message = message;
    }

    public SortOrder getId() {
        return id;
    }
    public void setId(SortOrder id) {
        this.id = id;
    }

    public SortOrder getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(SortOrder createdAt) {
        this.createdAt = createdAt;
    }

    public SortOrder getUserId() {
        return userId;
    }
    public void setUserId(SortOrder userId) {
        this.userId = userId;
    }

    public SortOrder getStatus() {
        return status;
    }
    public void setStatus(SortOrder status) {
        this.status = status;
    }

    public SortOrder getMessage() {
        return message;
    }
    public void setMessage(SortOrder message) {
        this.message = message;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SortOrder id;
        private SortOrder createdAt;
        private SortOrder userId;
        private SortOrder status;
        private SortOrder message;

        public Builder() {
        }

        public Builder setId(SortOrder id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(SortOrder createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUserId(SortOrder userId) {
            this.userId = userId;
            return this;
        }

        public Builder setStatus(SortOrder status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(SortOrder message) {
            this.message = message;
            return this;
        }


        public DeploymentOrderByInput build() {
            return new DeploymentOrderByInput(id, createdAt, userId, status, message);
        }

    }
}
