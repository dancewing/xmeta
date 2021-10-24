package io.xmeta.admin.model;


public class EntityFieldWhereInput implements java.io.Serializable {

    private StringFilter id;
    private StringFilter permanentId;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private StringFilter name;
    private StringFilter displayName;
    private EnumDataTypeFilter dataType;
    private BooleanFilter required;
    private BooleanFilter unique;
    private BooleanFilter searchable;
    private StringFilter description;

    public EntityFieldWhereInput() {
    }

    public EntityFieldWhereInput(StringFilter id, StringFilter permanentId, DateTimeFilter createdAt, DateTimeFilter updatedAt, StringFilter name, StringFilter displayName, EnumDataTypeFilter dataType, BooleanFilter required, BooleanFilter unique, BooleanFilter searchable, StringFilter description) {
        this.id = id;
        this.permanentId = permanentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.displayName = displayName;
        this.dataType = dataType;
        this.required = required;
        this.unique = unique;
        this.searchable = searchable;
        this.description = description;
    }

    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getPermanentId() {
        return permanentId;
    }
    public void setPermanentId(StringFilter permanentId) {
        this.permanentId = permanentId;
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

    public StringFilter getName() {
        return name;
    }
    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }
    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public EnumDataTypeFilter getDataType() {
        return dataType;
    }
    public void setDataType(EnumDataTypeFilter dataType) {
        this.dataType = dataType;
    }

    public BooleanFilter getRequired() {
        return required;
    }
    public void setRequired(BooleanFilter required) {
        this.required = required;
    }

    public BooleanFilter getUnique() {
        return unique;
    }
    public void setUnique(BooleanFilter unique) {
        this.unique = unique;
    }

    public BooleanFilter getSearchable() {
        return searchable;
    }
    public void setSearchable(BooleanFilter searchable) {
        this.searchable = searchable;
    }

    public StringFilter getDescription() {
        return description;
    }
    public void setDescription(StringFilter description) {
        this.description = description;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private StringFilter permanentId;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private StringFilter name;
        private StringFilter displayName;
        private EnumDataTypeFilter dataType;
        private BooleanFilter required;
        private BooleanFilter unique;
        private BooleanFilter searchable;
        private StringFilter description;

        public Builder() {
        }

        public Builder setId(StringFilter id) {
            this.id = id;
            return this;
        }

        public Builder setPermanentId(StringFilter permanentId) {
            this.permanentId = permanentId;
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

        public Builder setName(StringFilter name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(StringFilter displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDataType(EnumDataTypeFilter dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setRequired(BooleanFilter required) {
            this.required = required;
            return this;
        }

        public Builder setUnique(BooleanFilter unique) {
            this.unique = unique;
            return this;
        }

        public Builder setSearchable(BooleanFilter searchable) {
            this.searchable = searchable;
            return this;
        }

        public Builder setDescription(StringFilter description) {
            this.description = description;
            return this;
        }


        public EntityFieldWhereInput build() {
            return new EntityFieldWhereInput(id, permanentId, createdAt, updatedAt, name, displayName, dataType, required, unique, searchable, description);
        }

    }
}
