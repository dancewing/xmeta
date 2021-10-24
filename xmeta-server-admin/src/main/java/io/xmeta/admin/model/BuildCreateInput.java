package io.xmeta.admin.model;


public class BuildCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private WhereParentIdInput app;
    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput commit;

    public BuildCreateInput() {
    }

    public BuildCreateInput(WhereParentIdInput app, String message, WhereParentIdInput commit) {
        this.app = app;
        this.message = message;
        this.commit = commit;
    }

    public WhereParentIdInput getApp() {
        return app;
    }
    public void setApp(WhereParentIdInput app) {
        this.app = app;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public WhereParentIdInput getCommit() {
        return commit;
    }
    public void setCommit(WhereParentIdInput commit) {
        this.commit = commit;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private WhereParentIdInput app;
        private String message;
        private WhereParentIdInput commit;

        public Builder() {
        }

        public Builder setApp(WhereParentIdInput app) {
            this.app = app;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setCommit(WhereParentIdInput commit) {
            this.commit = commit;
            return this;
        }


        public BuildCreateInput build() {
            return new BuildCreateInput(app, message, commit);
        }

    }
}
