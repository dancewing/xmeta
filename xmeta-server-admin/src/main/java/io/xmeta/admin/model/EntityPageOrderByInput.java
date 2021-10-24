package io.xmeta.admin.model;


public class EntityPageOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder createdAt;
    private SortOrder updatedAt;
    private SortOrder blockType;
    private SortOrder displayName;
    private SortOrder description;

    public EntityPageOrderByInput() {
    }

    public EntityPageOrderByInput(SortOrder id, SortOrder createdAt, SortOrder updatedAt, SortOrder blockType, SortOrder displayName, SortOrder description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blockType = blockType;
        this.displayName = displayName;
        this.description = description;
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

    public SortOrder getBlockType() {
        return blockType;
    }
    public void setBlockType(SortOrder blockType) {
        this.blockType = blockType;
    }

    public SortOrder getDisplayName() {
        return displayName;
    }
    public void setDisplayName(SortOrder displayName) {
        this.displayName = displayName;
    }

    public SortOrder getDescription() {
        return description;
    }
    public void setDescription(SortOrder description) {
        this.description = description;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SortOrder id;
        private SortOrder createdAt;
        private SortOrder updatedAt;
        private SortOrder blockType;
        private SortOrder displayName;
        private SortOrder description;

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

        public Builder setBlockType(SortOrder blockType) {
            this.blockType = blockType;
            return this;
        }

        public Builder setDisplayName(SortOrder displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(SortOrder description) {
            this.description = description;
            return this;
        }


        public EntityPageOrderByInput build() {
            return new EntityPageOrderByInput(id, createdAt, updatedAt, blockType, displayName, description);
        }

    }
}
