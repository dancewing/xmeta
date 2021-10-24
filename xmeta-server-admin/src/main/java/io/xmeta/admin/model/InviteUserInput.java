package io.xmeta.admin.model;


public class InviteUserInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String email;

    public InviteUserInput() {
    }

    public InviteUserInput(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;

        public Builder() {
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }


        public InviteUserInput build() {
            return new InviteUserInput(email);
        }

    }
}
