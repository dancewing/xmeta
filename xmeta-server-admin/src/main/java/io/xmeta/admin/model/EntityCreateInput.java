package io.xmeta.admin.model;


public class EntityCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    private String table;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private String pluralDisplayName;
    private String description;
    @javax.validation.constraints.NotNull
    private WhereParentIdInput app;

    public EntityCreateInput() {
    }

    public EntityCreateInput(String name, String table, String displayName, String pluralDisplayName, String description, WhereParentIdInput app) {
        this.name = name;
        this.table = table;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.description = description;
        this.app = app;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPluralDisplayName() {
        return pluralDisplayName;
    }
    public void setPluralDisplayName(String pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
        private String table;
        private String displayName;
        private String pluralDisplayName;
        private String description;
        private WhereParentIdInput app;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTable(String table) {
            this.table = table;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setPluralDisplayName(String pluralDisplayName) {
            this.pluralDisplayName = pluralDisplayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setApp(WhereParentIdInput app) {
            this.app = app;
            return this;
        }


        public EntityCreateInput build() {
            return new EntityCreateInput(name, table, displayName, pluralDisplayName, description, app);
        }

    }
}
