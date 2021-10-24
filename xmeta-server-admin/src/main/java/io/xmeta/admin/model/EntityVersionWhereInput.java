package io.xmeta.admin.model;


public class EntityVersionWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private IntFilter versionNumber;
    private StringFilter name;
    private StringFilter table;
    private StringFilter displayName;
    private StringFilter pluralDisplayName;
    private StringFilter description;
    private StringFilter label;
    private WhereUniqueInput entity;

    public EntityVersionWhereInput() {
    }

    public EntityVersionWhereInput(StringFilter id, DateTimeFilter createdAt, DateTimeFilter updatedAt, IntFilter versionNumber, StringFilter name, StringFilter table, StringFilter displayName, StringFilter pluralDisplayName, StringFilter description, StringFilter label, WhereUniqueInput entity) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.versionNumber = versionNumber;
        this.name = name;
        this.table = table;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.description = description;
        this.label = label;
        this.entity = entity;
    }

    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }

    public DateTimeFilter getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(DateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }

    public DateTimeFilter getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(DateTimeFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public IntFilter getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(IntFilter versionNumber) {
        this.versionNumber = versionNumber;
    }

    public StringFilter getName() {
        return name;
    }
    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getTable() {
        return table;
    }
    public void setTable(StringFilter table) {
        this.table = table;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }
    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public StringFilter getPluralDisplayName() {
        return pluralDisplayName;
    }
    public void setPluralDisplayName(StringFilter pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public StringFilter getDescription() {
        return description;
    }
    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getLabel() {
        return label;
    }
    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public WhereUniqueInput getEntity() {
        return entity;
    }
    public void setEntity(WhereUniqueInput entity) {
        this.entity = entity;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private IntFilter versionNumber;
        private StringFilter name;
        private StringFilter table;
        private StringFilter displayName;
        private StringFilter pluralDisplayName;
        private StringFilter description;
        private StringFilter label;
        private WhereUniqueInput entity;

        public Builder() {
        }

        public Builder setId(StringFilter id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(DateTimeFilter createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(DateTimeFilter updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setVersionNumber(IntFilter versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public Builder setName(StringFilter name) {
            this.name = name;
            return this;
        }

        public Builder setTable(StringFilter table) {
            this.table = table;
            return this;
        }

        public Builder setDisplayName(StringFilter displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setPluralDisplayName(StringFilter pluralDisplayName) {
            this.pluralDisplayName = pluralDisplayName;
            return this;
        }

        public Builder setDescription(StringFilter description) {
            this.description = description;
            return this;
        }

        public Builder setLabel(StringFilter label) {
            this.label = label;
            return this;
        }

        public Builder setEntity(WhereUniqueInput entity) {
            this.entity = entity;
            return this;
        }


        public EntityVersionWhereInput build() {
            return new EntityVersionWhereInput(id, createdAt, updatedAt, versionNumber, name, table, displayName, pluralDisplayName, description, label, entity);
        }

    }
}
