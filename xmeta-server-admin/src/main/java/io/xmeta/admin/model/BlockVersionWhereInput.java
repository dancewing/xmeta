package io.xmeta.admin.model;


public class BlockVersionWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    private DateTimeFilter updatedAt;
    private IntFilter versionNumber;
    private StringFilter label;
    private WhereUniqueInput block;

    public BlockVersionWhereInput() {
    }

    public BlockVersionWhereInput(StringFilter id, DateTimeFilter createdAt, DateTimeFilter updatedAt, IntFilter versionNumber, StringFilter label, WhereUniqueInput block) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.versionNumber = versionNumber;
        this.label = label;
        this.block = block;
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

    public StringFilter getLabel() {
        return label;
    }
    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public WhereUniqueInput getBlock() {
        return block;
    }
    public void setBlock(WhereUniqueInput block) {
        this.block = block;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private DateTimeFilter createdAt;
        private DateTimeFilter updatedAt;
        private IntFilter versionNumber;
        private StringFilter label;
        private WhereUniqueInput block;

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

        public Builder setLabel(StringFilter label) {
            this.label = label;
            return this;
        }

        public Builder setBlock(WhereUniqueInput block) {
            this.block = block;
            return this;
        }


        public BlockVersionWhereInput build() {
            return new BlockVersionWhereInput(id, createdAt, updatedAt, versionNumber, label, block);
        }

    }
}
