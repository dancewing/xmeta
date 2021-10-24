package io.xmeta.admin.model;


public class EntityUpdateInput implements java.io.Serializable {

    private String name;
    private String table;
    private String displayName;
    private String pluralDisplayName;
    private String description;

    public EntityUpdateInput() {
    }

    public EntityUpdateInput(String name, String table, String displayName, String pluralDisplayName, String description) {
        this.name = name;
        this.table = table;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.description = description;
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



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String table;
        private String displayName;
        private String pluralDisplayName;
        private String description;

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


        public EntityUpdateInput build() {
            return new EntityUpdateInput(name, table, displayName, pluralDisplayName, description);
        }

    }
}
