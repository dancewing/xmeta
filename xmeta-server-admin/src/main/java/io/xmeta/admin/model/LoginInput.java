package io.xmeta.admin.model;


public class LoginInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String email;
    @javax.validation.constraints.NotNull
    private String password;

    public LoginInput() {
    }

    public LoginInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

        private String email;
        private String password;

        public Builder() {
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }


        public LoginInput build() {
            return new LoginInput(email, password);
        }

    }
}
