package io.xmeta.admin.model;


public class Block implements java.io.Serializable, PendingChangeResource {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    private App app;
    private Block parentBlock;
    @javax.validation.constraints.NotNull
    private String displayName;
    private String description;
    @javax.validation.constraints.NotNull
    private EnumBlockType blockType;
    private Double versionNumber;
    private String lockedByUserId;
    private java.time.ZonedDateTime lockedAt;
    @javax.validation.constraints.NotNull
    private java.util.List<User> lockedByUser;

    public Block() {
    }

    public Block(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, App app, Block parentBlock, String displayName, String description, EnumBlockType blockType, Double versionNumber, String lockedByUserId, java.time.ZonedDateTime lockedAt, java.util.List<User> lockedByUser) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.app = app;
        this.parentBlock = parentBlock;
        this.displayName = displayName;
        this.description = description;
        this.blockType = blockType;
        this.versionNumber = versionNumber;
        this.lockedByUserId = lockedByUserId;
        this.lockedAt = lockedAt;
        this.lockedByUser = lockedByUser;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public java.time.ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.time.ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.time.ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public App getApp() {
        return app;
    }
    public void setApp(App app) {
        this.app = app;
    }

    public Block getParentBlock() {
        return parentBlock;
    }
    public void setParentBlock(Block parentBlock) {
        this.parentBlock = parentBlock;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public EnumBlockType getBlockType() {
        return blockType;
    }
    public void setBlockType(EnumBlockType blockType) {
        this.blockType = blockType;
    }

    public Double getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(Double versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getLockedByUserId() {
        return lockedByUserId;
    }
    public void setLockedByUserId(String lockedByUserId) {
        this.lockedByUserId = lockedByUserId;
    }

    public java.time.ZonedDateTime getLockedAt() {
        return lockedAt;
    }
    public void setLockedAt(java.time.ZonedDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    public java.util.List<User> getLockedByUser() {
        return lockedByUser;
    }
    public void setLockedByUser(java.util.List<User> lockedByUser) {
        this.lockedByUser = lockedByUser;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private App app;
        private Block parentBlock;
        private String displayName;
        private String description;
        private EnumBlockType blockType;
        private Double versionNumber;
        private String lockedByUserId;
        private java.time.ZonedDateTime lockedAt;
        private java.util.List<User> lockedByUser;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(java.time.ZonedDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(java.time.ZonedDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setApp(App app) {
            this.app = app;
            return this;
        }

        public Builder setParentBlock(Block parentBlock) {
            this.parentBlock = parentBlock;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setBlockType(EnumBlockType blockType) {
            this.blockType = blockType;
            return this;
        }

        public Builder setVersionNumber(Double versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public Builder setLockedByUserId(String lockedByUserId) {
            this.lockedByUserId = lockedByUserId;
            return this;
        }

        public Builder setLockedAt(java.time.ZonedDateTime lockedAt) {
            this.lockedAt = lockedAt;
            return this;
        }

        public Builder setLockedByUser(java.util.List<User> lockedByUser) {
            this.lockedByUser = lockedByUser;
            return this;
        }


        public Block build() {
            return new Block(id, createdAt, updatedAt, app, parentBlock, displayName, description, blockType, versionNumber, lockedByUserId, lockedAt, lockedByUser);
        }

    }
}
