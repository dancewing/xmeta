package io.xmeta.admin.model;


public class EntityFieldOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder permanentId;
    private SortOrder createdAt;
    private SortOrder updatedAt;
    private SortOrder name;
    private SortOrder displayName;
    private SortOrder dataType;
    private SortOrder required;
    private SortOrder unique;
    private SortOrder searchable;
    private SortOrder description;
    private SortOrder position;

    public EntityFieldOrderByInput() {
    }

    public EntityFieldOrderByInput(SortOrder id, SortOrder permanentId, SortOrder createdAt, SortOrder updatedAt, SortOrder name, SortOrder displayName, SortOrder dataType, SortOrder required, SortOrder unique, SortOrder searchable, SortOrder description, SortOrder position) {
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
        this.position = position;
    }

    public SortOrder getId() {
        return id;
    }
    public void setId(SortOrder id) {
        this.id = id;
    }

    public SortOrder getPermanentId() {
        return permanentId;
    }
    public void setPermanentId(SortOrder permanentId) {
        this.permanentId = permanentId;
    }

    public SortOrder getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(SortOrder createdAt) {
        this.createdAt = createdAt;
    }

    public SortOrder getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(SortOrder updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SortOrder getName() {
        return name;
    }
    public void setName(SortOrder name) {
        this.name = name;
    }

    public SortOrder getDisplayName() {
        return displayName;
    }
    public void setDisplayName(SortOrder displayName) {
        this.displayName = displayName;
    }

    public SortOrder getDataType() {
        return dataType;
    }
    public void setDataType(SortOrder dataType) {
        this.dataType = dataType;
    }

    public SortOrder getRequired() {
        return required;
    }
    public void setRequired(SortOrder required) {
        this.required = required;
    }

    public SortOrder getUnique() {
        return unique;
    }
    public void setUnique(SortOrder unique) {
        this.unique = unique;
    }

    public SortOrder getSearchable() {
        return searchable;
    }
    public void setSearchable(SortOrder searchable) {
        this.searchable = searchable;
    }

    public SortOrder getDescription() {
        return description;
    }
    public void setDescription(SortOrder description) {
        this.description = description;
    }

    public SortOrder getPosition() {
        return position;
    }
    public void setPosition(SortOrder position) {
        this.position = position;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SortOrder id;
        private SortOrder permanentId;
        private SortOrder createdAt;
        private SortOrder updatedAt;
        private SortOrder name;
        private SortOrder displayName;
        private SortOrder dataType;
        private SortOrder required;
        private SortOrder unique;
        private SortOrder searchable;
        private SortOrder description;
        private SortOrder position;

        public Builder() {
        }

        public Builder setId(SortOrder id) {
            this.id = id;
            return this;
        }

        public Builder setPermanentId(SortOrder permanentId) {
            this.permanentId = permanentId;
            return this;
        }

        public Builder setCreatedAt(SortOrder createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(SortOrder updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setName(SortOrder name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(SortOrder displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDataType(SortOrder dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder setRequired(SortOrder required) {
            this.required = required;
            return this;
        }

        public Builder setUnique(SortOrder unique) {
            this.unique = unique;
            return this;
        }

        public Builder setSearchable(SortOrder searchable) {
            this.searchable = searchable;
            return this;
        }

        public Builder setDescription(SortOrder description) {
            this.description = description;
            return this;
        }

        public Builder setPosition(SortOrder position) {
            this.position = position;
            return this;
        }


        public EntityFieldOrderByInput build() {
            return new EntityFieldOrderByInput(id, permanentId, createdAt, updatedAt, name, displayName, dataType, required, unique, searchable, description, position);
        }

    }
}
