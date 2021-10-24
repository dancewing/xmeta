package io.xmeta.admin.model;


public class SignupInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String email;
    @javax.validation.constraints.NotNull
    private String password;
    @javax.validation.constraints.NotNull
    private String firstName;
    @javax.validation.constraints.NotNull
    private String lastName;
    @javax.validation.constraints.NotNull
    private String workspaceName;

    public SignupInput() {
    }

    public SignupInput(String email, String password, String firstName, String lastName, String workspaceName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workspaceName = workspaceName;
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

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }
    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String workspaceName;

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

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setWorkspaceName(String workspaceName) {
            this.workspaceName = workspaceName;
            return this;
        }


        public SignupInput build() {
            return new SignupInput(email, password, firstName, lastName, workspaceName);
        }

    }
}
