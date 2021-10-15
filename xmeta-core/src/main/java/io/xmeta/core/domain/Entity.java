package io.xmeta.core.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * @author jeff_qian
 */
public class Entity implements java.io.Serializable {

    private String id;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private String pluralDisplayName;
    @javax.validation.constraints.NotNull
    private String table;
    private String description;
    private List<EntityField> fields = new ArrayList<>();

    public Entity() {
    }

    public Entity(String id, String name, String displayName, String pluralDisplayName, String table, String description, List<EntityField> fields) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.table = table;
        this.description = description;
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public void setFields(List<EntityField> fields) {
        this.fields = fields;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private String displayName;
        private String pluralDisplayName;
        private String table;
        private String description;
        private List<EntityField> fields;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
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

        public Builder setTable(String table) {
            this.table = table;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setFields(List<EntityField> fields) {
            this.fields = fields;
            return this;
        }

        public Entity build() {
            return new Entity(id, name, displayName, pluralDisplayName, table, description, fields);
        }

    }
}
