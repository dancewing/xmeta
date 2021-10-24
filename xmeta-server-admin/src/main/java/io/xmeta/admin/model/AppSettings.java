package io.xmeta.admin.model;


public class AppSettings implements java.io.Serializable, IBlock {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    private Block parentBlock;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private String description;
    @javax.validation.constraints.NotNull
    private EnumBlockType blockType;
    private double versionNumber;
    @javax.validation.constraints.NotNull
    private java.util.List<BlockInputOutput> inputParameters;
    @javax.validation.constraints.NotNull
    private java.util.List<BlockInputOutput> outputParameters;
    private String lockedByUserId;
    private java.time.ZonedDateTime lockedAt;
    @javax.validation.constraints.NotNull
    private String dbHost;
    @javax.validation.constraints.NotNull
    private String dbName;
    @javax.validation.constraints.NotNull
    private String dbUser;
    @javax.validation.constraints.NotNull
    private String dbPassword;
    private int dbPort;
    @javax.validation.constraints.NotNull
    private EnumAuthProviderType authProvider;

    public AppSettings() {
    }

    public AppSettings(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, Block parentBlock, String displayName, String description, EnumBlockType blockType, double versionNumber, java.util.List<BlockInputOutput> inputParameters, java.util.List<BlockInputOutput> outputParameters, String lockedByUserId, java.time.ZonedDateTime lockedAt, String dbHost, String dbName, String dbUser, String dbPassword, int dbPort, EnumAuthProviderType authProvider) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parentBlock = parentBlock;
        this.displayName = displayName;
        this.description = description;
        this.blockType = blockType;
        this.versionNumber = versionNumber;
        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
        this.lockedByUserId = lockedByUserId;
        this.lockedAt = lockedAt;
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbPort = dbPort;
        this.authProvider = authProvider;
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

    public double getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(double versionNumber) {
        this.versionNumber = versionNumber;
    }

    public java.util.List<BlockInputOutput> getInputParameters() {
        return inputParameters;
    }
    public void setInputParameters(java.util.List<BlockInputOutput> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public java.util.List<BlockInputOutput> getOutputParameters() {
        return outputParameters;
    }
    public void setOutputParameters(java.util.List<BlockInputOutput> outputParameters) {
        this.outputParameters = outputParameters;
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

    public String getDbHost() {
        return dbHost;
    }
    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbName() {
        return dbName;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getDbPort() {
        return dbPort;
    }
    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public EnumAuthProviderType getAuthProvider() {
        return authProvider;
    }
    public void setAuthProvider(EnumAuthProviderType authProvider) {
        this.authProvider = authProvider;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private Block parentBlock;
        private String displayName;
        private String description;
        private EnumBlockType blockType;
        private double versionNumber;
        private java.util.List<BlockInputOutput> inputParameters;
        private java.util.List<BlockInputOutput> outputParameters;
        private String lockedByUserId;
        private java.time.ZonedDateTime lockedAt;
        private String dbHost;
        private String dbName;
        private String dbUser;
        private String dbPassword;
        private int dbPort;
        private EnumAuthProviderType authProvider;

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

        public Builder setVersionNumber(double versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public Builder setInputParameters(java.util.List<BlockInputOutput> inputParameters) {
            this.inputParameters = inputParameters;
            return this;
        }

        public Builder setOutputParameters(java.util.List<BlockInputOutput> outputParameters) {
            this.outputParameters = outputParameters;
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

        public Builder setDbHost(String dbHost) {
            this.dbHost = dbHost;
            return this;
        }

        public Builder setDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Builder setDbUser(String dbUser) {
            this.dbUser = dbUser;
            return this;
        }

        public Builder setDbPassword(String dbPassword) {
            this.dbPassword = dbPassword;
            return this;
        }

        public Builder setDbPort(int dbPort) {
            this.dbPort = dbPort;
            return this;
        }

        public Builder setAuthProvider(EnumAuthProviderType authProvider) {
            this.authProvider = authProvider;
            return this;
        }


        public AppSettings build() {
            return new AppSettings(id, createdAt, updatedAt, parentBlock, displayName, description, blockType, versionNumber, inputParameters, outputParameters, lockedByUserId, lockedAt, dbHost, dbName, dbUser, dbPassword, dbPort, authProvider);
        }

    }
}
