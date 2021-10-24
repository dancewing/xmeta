package io.xmeta.admin.model;


public class CommitOrderByInput implements java.io.Serializable {

    private SortOrder id;
    private SortOrder createdAt;
    private SortOrder message;

    public CommitOrderByInput() {
    }

    public CommitOrderByInput(SortOrder id, SortOrder createdAt, SortOrder message) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
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

    public SortOrder getMessage() {
        return message;
    }
    public void setMessage(SortOrder message) {
        this.message = message;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private SortOrder id;
        private SortOrder createdAt;
        private SortOrder message;

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

        public Builder setMessage(SortOrder message) {
            this.message = message;
            return this;
        }


        public CommitOrderByInput build() {
            return new CommitOrderByInput(id, createdAt, message);
        }

    }
}
