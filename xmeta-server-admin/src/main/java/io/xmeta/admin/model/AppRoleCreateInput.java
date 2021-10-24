package io.xmeta.admin.model;


public class AppRoleCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String description;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput app;

    public AppRoleCreateInput() {
    }

    public AppRoleCreateInput(String name, String description, String displayName, WhereParentIdInput app) {
        this.name = name;
        this.description = description;
        this.displayName = displayName;
        this.app = app;
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

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

        private String name;
        private String description;
        private String displayName;
        private WhereParentIdInput app;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setApp(WhereParentIdInput app) {
            this.app = app;
            return this;
        }


        public AppRoleCreateInput build() {
            return new AppRoleCreateInput(name, description, displayName, app);
        }

    }
}
