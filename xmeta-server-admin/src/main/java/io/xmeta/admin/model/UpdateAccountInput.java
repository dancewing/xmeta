package io.xmeta.admin.model;


public class UpdateAccountInput implements java.io.Serializable {

    private String firstName;
    private String lastName;

    public UpdateAccountInput() {
    }

    public UpdateAccountInput(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String firstName;
        private String lastName;

        public Builder() {
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }


        public UpdateAccountInput build() {
            return new UpdateAccountInput(firstName, lastName);
        }

    }
}
