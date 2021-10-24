package io.xmeta.admin.model;


public class EnvironmentCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String appId;
    @javax.validation.constraints.NotNull
    private String name;
    private String description;
    @javax.validation.constraints.NotNull
    private String address;
    @javax.validation.constraints.NotNull
    private String user;
    @javax.validation.constraints.NotNull
    private String password;

    public EnvironmentCreateInput() {
    }

    public EnvironmentCreateInput(String appId, String name, String description, String address, String user, String password) {
        this.appId = appId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.user = user;
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String appId;
        private String name;
        private String description;
        private String address;
        private String user;
        private String password;

        public Builder() {
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }


        public EnvironmentCreateInput build() {
            return new EnvironmentCreateInput(appId, name, description, address, user, password);
        }

    }
}
