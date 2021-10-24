package io.xmeta.admin.model;


public class Auth implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String token;

    public Auth() {
    }

    public Auth(String token) {
        this.token = token;
    }

    /**
     * JWT Bearer token
     */
    public String getToken() {
        return token;
    }
    /**
     * JWT Bearer token
     */
    public void setToken(String token) {
        this.token = token;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String token;

        public Builder() {
        }

        /**
         * JWT Bearer token
         */
        public Builder setToken(String token) {
            this.token = token;
            return this;
        }


        public Auth build() {
            return new Auth(token);
        }

    }
}
