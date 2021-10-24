package io.xmeta.admin.model;


public class AppWhereInput implements java.io.Serializable {

    private String id;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private StringFilter name;
    private StringFilter description;

    public AppWhereInput() {
    }

    public AppWhereInput(String id, DateTimeFilter createdAt, DateTimeFilter updatedAt, StringFilter name, StringFilter description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public StringFilter getName() {
        return name;
    }
    public void setName(StringFilter name) {
        this.name = name;
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

        private String id;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private StringFilter name;
        private StringFilter description;

        public Builder() {
        }

        public Builder setId(String id) {
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

        public Builder setName(StringFilter name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(StringFilter description) {
            this.description = description;
            return this;
        }


        public AppWhereInput build() {
            return new AppWhereInput(id, createdAt, updatedAt, name, description);
        }

    }
}
