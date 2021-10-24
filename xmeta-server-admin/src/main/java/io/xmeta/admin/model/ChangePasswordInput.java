package io.xmeta.admin.model;


public class ChangePasswordInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String oldPassword;
    @javax.validation.constraints.NotNull
    private String newPassword;

    public ChangePasswordInput() {
    }

    public ChangePasswordInput(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String oldPassword;
        private String newPassword;

        public Builder() {
        }

        public Builder setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        public Builder setNewPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }


        public ChangePasswordInput build() {
            return new ChangePasswordInput(oldPassword, newPassword);
        }

    }
}
