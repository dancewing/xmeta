package io.xmeta.admin.model;


public class App implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String description;
    @javax.validation.constraints.NotNull
    private String color;
    @javax.validation.constraints.NotNull
    private java.util.List<Environment> environments;
    private java.time.ZonedDateTime githubTokenCreatedDate;
    private boolean githubSyncEnabled;
    private String githubRepo;
    private String githubBranch;
    private java.time.ZonedDateTime githubLastSync;
    private String githubLastMessage;

    public App() {
    }

    public App(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, String name, String description, String color, java.util.List<Environment> environments, java.time.ZonedDateTime githubTokenCreatedDate, boolean githubSyncEnabled, String githubRepo, String githubBranch, java.time.ZonedDateTime githubLastSync, String githubLastMessage) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
        this.color = color;
        this.environments = environments;
        this.githubTokenCreatedDate = githubTokenCreatedDate;
        this.githubSyncEnabled = githubSyncEnabled;
        this.githubRepo = githubRepo;
        this.githubBranch = githubBranch;
        this.githubLastSync = githubLastSync;
        this.githubLastMessage = githubLastMessage;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public java.util.List<Environment> getEnvironments() {
        return environments;
    }
    public void setEnvironments(java.util.List<Environment> environments) {
        this.environments = environments;
    }

    public java.time.ZonedDateTime getGithubTokenCreatedDate() {
        return githubTokenCreatedDate;
    }
    public void setGithubTokenCreatedDate(java.time.ZonedDateTime githubTokenCreatedDate) {
        this.githubTokenCreatedDate = githubTokenCreatedDate;
    }

    public boolean getGithubSyncEnabled() {
        return githubSyncEnabled;
    }
    public void setGithubSyncEnabled(boolean githubSyncEnabled) {
        this.githubSyncEnabled = githubSyncEnabled;
    }

    public String getGithubRepo() {
        return githubRepo;
    }
    public void setGithubRepo(String githubRepo) {
        this.githubRepo = githubRepo;
    }

    public String getGithubBranch() {
        return githubBranch;
    }
    public void setGithubBranch(String githubBranch) {
        this.githubBranch = githubBranch;
    }

    public java.time.ZonedDateTime getGithubLastSync() {
        return githubLastSync;
    }
    public void setGithubLastSync(java.time.ZonedDateTime githubLastSync) {
        this.githubLastSync = githubLastSync;
    }

    public String getGithubLastMessage() {
        return githubLastMessage;
    }
    public void setGithubLastMessage(String githubLastMessage) {
        this.githubLastMessage = githubLastMessage;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private String name;
        private String description;
        private String color;
        private java.util.List<Environment> environments;
        private java.time.ZonedDateTime githubTokenCreatedDate;
        private boolean githubSyncEnabled;
        private String githubRepo;
        private String githubBranch;
        private java.time.ZonedDateTime githubLastSync;
        private String githubLastMessage;

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

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setEnvironments(java.util.List<Environment> environments) {
            this.environments = environments;
            return this;
        }

        public Builder setGithubTokenCreatedDate(java.time.ZonedDateTime githubTokenCreatedDate) {
            this.githubTokenCreatedDate = githubTokenCreatedDate;
            return this;
        }

        public Builder setGithubSyncEnabled(boolean githubSyncEnabled) {
            this.githubSyncEnabled = githubSyncEnabled;
            return this;
        }

        public Builder setGithubRepo(String githubRepo) {
            this.githubRepo = githubRepo;
            return this;
        }

        public Builder setGithubBranch(String githubBranch) {
            this.githubBranch = githubBranch;
            return this;
        }

        public Builder setGithubLastSync(java.time.ZonedDateTime githubLastSync) {
            this.githubLastSync = githubLastSync;
            return this;
        }

        public Builder setGithubLastMessage(String githubLastMessage) {
            this.githubLastMessage = githubLastMessage;
            return this;
        }


        public App build() {
            return new App(id, createdAt, updatedAt, name, description, color, environments, githubTokenCreatedDate, githubSyncEnabled, githubRepo, githubBranch, githubLastSync, githubLastMessage);
        }

    }
}
