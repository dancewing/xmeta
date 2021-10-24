package io.xmeta.admin.model;


public class PendingChangesFindInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private WhereUniqueInput app;

    public PendingChangesFindInput() {
    }

    public PendingChangesFindInput(WhereUniqueInput app) {
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


        public PendingChangesFindInput build() {
            return new PendingChangesFindInput(app);
        }

    }
}
