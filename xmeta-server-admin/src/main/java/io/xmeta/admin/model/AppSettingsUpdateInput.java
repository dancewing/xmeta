package io.xmeta.admin.model;


public class AppSettingsUpdateInput implements java.io.Serializable {

    private String displayName;
    private String description;
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

    public AppSettingsUpdateInput() {
    }

    public AppSettingsUpdateInput(String displayName, String description, String dbHost, String dbName, String dbUser, String dbPassword, int dbPort, EnumAuthProviderType authProvider) {
        this.displayName = displayName;
        this.description = description;
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbPort = dbPort;
        this.authProvider = authProvider;
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

        private String displayName;
        private String description;
        private String dbHost;
        private String dbName;
        private String dbUser;
        private String dbPassword;
        private int dbPort;
        private EnumAuthProviderType authProvider;

        public Builder() {
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
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


        public AppSettingsUpdateInput build() {
            return new AppSettingsUpdateInput(displayName, description, dbHost, dbName, dbUser, dbPassword, dbPort, authProvider);
        }

    }
}
