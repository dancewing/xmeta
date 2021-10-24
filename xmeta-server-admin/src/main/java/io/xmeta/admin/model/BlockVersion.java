package io.xmeta.admin.model;


public class BlockVersion implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private String displayName;
    private String description;
    @javax.validation.constraints.NotNull
    private Block block;
    private int versionNumber;
    private Commit commit;
    private java.util.Map<String, Object> settings;

    public BlockVersion() {
    }

    public BlockVersion(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, String displayName, String description, Block block, int versionNumber, Commit commit, java.util.Map<String, Object> settings) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.displayName = displayName;
        this.description = description;
        this.block = block;
        this.versionNumber = versionNumber;
        this.commit = commit;
        this.settings = settings;
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

    public Block getBlock() {
        return block;
    }
    public void setBlock(Block block) {
        this.block = block;
    }

    public int getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Commit getCommit() {
        return commit;
    }
    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public java.util.Map<String, Object> getSettings() {
        return settings;
    }
    public void setSettings(java.util.Map<String, Object> settings) {
        this.settings = settings;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private String displayName;
        private String description;
        private Block block;
        private int versionNumber;
        private Commit commit;
        private java.util.Map<String, Object> settings;

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

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setBlock(Block block) {
            this.block = block;
            return this;
        }

        public Builder setVersionNumber(int versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public Builder setCommit(Commit commit) {
            this.commit = commit;
            return this;
        }

        public Builder setSettings(java.util.Map<String, Object> settings) {
            this.settings = settings;
            return this;
        }


        public BlockVersion build() {
            return new BlockVersion(id, createdAt, updatedAt, displayName, description, block, versionNumber, commit, settings);
        }

    }
}
