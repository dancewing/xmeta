package io.xmeta.admin.model;


public class AppEnableSyncWithGithubRepoInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String githubRepo;
    private String githubBranch;

    public AppEnableSyncWithGithubRepoInput() {
    }

    public AppEnableSyncWithGithubRepoInput(String githubRepo, String githubBranch) {
        this.githubRepo = githubRepo;
        this.githubBranch = githubBranch;
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



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String githubRepo;
        private String githubBranch;

        public Builder() {
        }

        public Builder setGithubRepo(String githubRepo) {
            this.githubRepo = githubRepo;
            return this;
        }

        public Builder setGithubBranch(String githubBranch) {
            this.githubBranch = githubBranch;
            return this;
        }


        public AppEnableSyncWithGithubRepoInput build() {
            return new AppEnableSyncWithGithubRepoInput(githubRepo, githubBranch);
        }

    }
}
