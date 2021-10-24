package io.xmeta.admin.model;


public class BlockVersionOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder createdAt;
    private SortOrder updatedAt;
    private SortOrder versionNumber;
    private SortOrder label;

    public BlockVersionOrderByInput() {
    }

    public BlockVersionOrderByInput(SortOrder id, SortOrder createdAt, SortOrder updatedAt, SortOrder versionNumber, SortOrder label) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.versionNumber = versionNumber;
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

        public Builder setLabel(SortOrder label) {
            this.label = label;
            return this;
        }


        public BlockVersionOrderByInput build() {
            return new BlockVersionOrderByInput(id, createdAt, updatedAt, versionNumber, label);
        }

    }
}
