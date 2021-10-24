package io.xmeta.admin.model;


public class EntityVersionOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder createdAt;
    private SortOrder updatedAt;
    private SortOrder versionNumber;
    private SortOrder name;
    private SortOrder displayName;
    private SortOrder pluralDisplayName;
    private SortOrder description;
    private SortOrder label;

    public EntityVersionOrderByInput() {
    }

    public EntityVersionOrderByInput(SortOrder id, SortOrder createdAt, SortOrder updatedAt, SortOrder versionNumber, SortOrder name, SortOrder displayName, SortOrder pluralDisplayName, SortOrder description, SortOrder label) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.versionNumber = versionNumber;
        this.name = name;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.description = description;
        this.label = label;
    }

    public SortOrder getId() {
        return id;
    }
    public void setId(SortOrder id) {
        this.id = id;
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

    public SortOrder getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(SortOrder versionNumber) {
        this.versionNumber = versionNumber;
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

    public SortOrder getPluralDisplayName() {
        return pluralDisplayName;
    }
    public void setPluralDisplayName(SortOrder pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public SortOrder getDescription() {
        return description;
    }
    public void setDescription(SortOrder description) {
        this.description = description;
    }

    public SortOrder getLabel() {
        return label;
    }
    public void setLabel(SortOrder label) {
        this.label = label;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SortOrder id;
        private SortOrder createdAt;
        private SortOrder updatedAt;
        private SortOrder versionNumber;
        private SortOrder name;
        private SortOrder displayName;
        private SortOrder pluralDisplayName;
        private SortOrder description;
        private SortOrder label;

        public Builder() {
        }

        public Builder setId(SortOrder id) {
            this.id = id;
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

        public Builder setVersionNumber(SortOrder versionNumber) {
            this.versionNumber = versionNumber;
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

        public Builder setPluralDisplayName(SortOrder pluralDisplayName) {
            this.pluralDisplayName = pluralDisplayName;
            return this;
        }

        public Builder setDescription(SortOrder description) {
            this.description = description;
            return this;
        }

        public Builder setLabel(SortOrder label) {
            this.label = label;
            return this;
        }


        public EntityVersionOrderByInput build() {
            return new EntityVersionOrderByInput(id, createdAt, updatedAt, versionNumber, name, displayName, pluralDisplayName, description, label);
        }

    }
}
