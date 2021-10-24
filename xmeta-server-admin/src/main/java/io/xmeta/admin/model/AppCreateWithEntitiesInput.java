package io.xmeta.admin.model;


public class AppCreateWithEntitiesInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private AppCreateInput app;
    @javax.validation.constraints.NotNull
    private java.util.List<AppCreateWithEntitiesEntityInput> entities;
    @javax.validation.constraints.NotNull
    private String commitMessage;

    public AppCreateWithEntitiesInput() {
    }

    public AppCreateWithEntitiesInput(AppCreateInput app, java.util.List<AppCreateWithEntitiesEntityInput> entities, String commitMessage) {
        this.app = app;
        this.entities = entities;
        this.commitMessage = commitMessage;
    }

    public AppCreateInput getApp() {
        return app;
    }
    public void setApp(AppCreateInput app) {
        this.app = app;
    }

    public java.util.List<AppCreateWithEntitiesEntityInput> getEntities() {
        return entities;
    }
    public void setEntities(java.util.List<AppCreateWithEntitiesEntityInput> entities) {
        this.entities = entities;
    }

    public String getCommitMessage() {
        return commitMessage;
    }
    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private AppCreateInput app;
        private java.util.List<AppCreateWithEntitiesEntityInput> entities;
        private String commitMessage;

        public Builder() {
        }

        public Builder setApp(AppCreateInput app) {
            this.app = app;
            return this;
        }

        public Builder setEntities(java.util.List<AppCreateWithEntitiesEntityInput> entities) {
            this.entities = entities;
            return this;
        }

        public Builder setCommitMessage(String commitMessage) {
            this.commitMessage = commitMessage;
            return this;
        }


        public AppCreateWithEntitiesInput build() {
            return new AppCreateWithEntitiesInput(app, entities, commitMessage);
        }

    }
}
