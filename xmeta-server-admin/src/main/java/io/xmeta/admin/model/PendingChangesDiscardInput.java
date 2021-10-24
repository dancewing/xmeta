package io.xmeta.admin.model;


public class PendingChangesDiscardInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private WhereParentIdInput app;

    public PendingChangesDiscardInput() {
    }

    public PendingChangesDiscardInput(WhereParentIdInput app) {
        this.app = app;
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

        private WhereParentIdInput app;

        public Builder() {
        }

        public Builder setApp(WhereParentIdInput app) {
            this.app = app;
            return this;
        }


        public PendingChangesDiscardInput build() {
            return new PendingChangesDiscardInput(app);
        }

    }
}
