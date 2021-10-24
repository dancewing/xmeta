package io.xmeta.admin.model;


public class AppOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder createdAt;
    private SortOrder updatedAt;
    private SortOrder name;
    private SortOrder description;

    public AppOrderByInput() {
    }

    public AppOrderByInput(SortOrder id, SortOrder createdAt, SortOrder updatedAt, SortOrder name, SortOrder description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
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

    public SortOrder getName() {
        return name;
    }
    public void setName(SortOrder name) {
        this.name = name;
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
        private SortOrder name;
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

        public Builder setName(SortOrder name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(SortOrder description) {
            this.description = description;
            return this;
        }


        public AppOrderByInput build() {
            return new AppOrderByInput(id, createdAt, updatedAt, name, description);
        }

    }
}
