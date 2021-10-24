package io.xmeta.admin.model;


public class AppValidationResult implements java.io.Serializable {

    private boolean isValid;
    @javax.validation.constraints.NotNull
    private java.util.List<AppValidationErrorTypes> messages;

    public AppValidationResult() {
    }

    public AppValidationResult(boolean isValid, java.util.List<AppValidationErrorTypes> messages) {
        this.isValid = isValid;
        this.messages = messages;
    }

    public boolean getIsValid() {
        return isValid;
    }
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public java.util.List<AppValidationErrorTypes> getMessages() {
        return messages;
    }
    public void setMessages(java.util.List<AppValidationErrorTypes> messages) {
        this.messages = messages;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean isValid;
        private java.util.List<AppValidationErrorTypes> messages;

        public Builder() {
        }

        public Builder setIsValid(boolean isValid) {
            this.isValid = isValid;
            return this;
        }

        public Builder setMessages(java.util.List<AppValidationErrorTypes> messages) {
            this.messages = messages;
            return this;
        }


        public AppValidationResult build() {
            return new AppValidationResult(isValid, messages);
        }

    }
}
