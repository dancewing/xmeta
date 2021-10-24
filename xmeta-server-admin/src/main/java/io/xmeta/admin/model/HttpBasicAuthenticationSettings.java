package io.xmeta.admin.model;


public class HttpBasicAuthenticationSettings implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String username;
    @javax.validation.constraints.NotNull
    private String password;

    public HttpBasicAuthenticationSettings() {
    }

    public HttpBasicAuthenticationSettings(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

        private String username;
        private String password;

        public Builder() {
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }


        public HttpBasicAuthenticationSettings build() {
            return new HttpBasicAuthenticationSettings(username, password);
        }

    }
}
