package io.xmeta.admin.model;


public class CommitCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput app;

    public CommitCreateInput() {
    }

    public CommitCreateInput(String message, WhereParentIdInput app) {
        this.message = message;
        this.app = app;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public WhereParentIdInput getApp() {
        return app;
    }
    public void setApp(WhereParentIdInput app) {
        this.app = app;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String message;
        private WhereParentIdInput app;

        public Builder() {
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setApp(WhereParentIdInput app) {
            this.app = app;
            return this;
        }


        public CommitCreateInput build() {
            return new CommitCreateInput(message, app);
        }

    }
}
