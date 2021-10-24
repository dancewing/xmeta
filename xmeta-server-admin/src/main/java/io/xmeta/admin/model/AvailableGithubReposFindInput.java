package io.xmeta.admin.model;


public class AvailableGithubReposFindInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private WhereUniqueInput app;

    public AvailableGithubReposFindInput() {
    }

    public AvailableGithubReposFindInput(WhereUniqueInput app) {
        this.app = app;
    }

    public WhereUniqueInput getApp() {
        return app;
    }
    public void setApp(WhereUniqueInput app) {
        this.app = app;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private WhereUniqueInput app;

        public Builder() {
        }

        public Builder setApp(WhereUniqueInput app) {
            this.app = app;
            return this;
        }


        public AvailableGithubReposFindInput build() {
            return new AvailableGithubReposFindInput(app);
        }

    }
}
