package io.xmeta.admin.model;


public class ApiToken implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String userId;
    private String token;
    @javax.validation.constraints.NotNull
    private String previewChars;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime lastAccessAt;

    public ApiToken() {
    }

    public ApiToken(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, String name, String userId, String token, String previewChars, java.time.ZonedDateTime lastAccessAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.userId = userId;
        this.token = token;
        this.previewChars = previewChars;
        this.lastAccessAt = lastAccessAt;
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

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getPreviewChars() {
        return previewChars;
    }
    public void setPreviewChars(String previewChars) {
        this.previewChars = previewChars;
    }

    public java.time.ZonedDateTime getLastAccessAt() {
        return lastAccessAt;
    }
    public void setLastAccessAt(java.time.ZonedDateTime lastAccessAt) {
        this.lastAccessAt = lastAccessAt;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private String name;
        private String userId;
        private String token;
        private String previewChars;
        private java.time.ZonedDateTime lastAccessAt;

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

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setPreviewChars(String previewChars) {
            this.previewChars = previewChars;
            return this;
        }

        public Builder setLastAccessAt(java.time.ZonedDateTime lastAccessAt) {
            this.lastAccessAt = lastAccessAt;
            return this;
        }


        public ApiToken build() {
            return new ApiToken(id, createdAt, updatedAt, name, userId, token, previewChars, lastAccessAt);
        }

    }
}
