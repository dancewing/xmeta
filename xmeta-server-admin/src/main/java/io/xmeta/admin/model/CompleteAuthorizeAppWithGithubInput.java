package io.xmeta.admin.model;


public class CompleteAuthorizeAppWithGithubInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String code;
    @javax.validation.constraints.NotNull
    private String state;

    public CompleteAuthorizeAppWithGithubInput() {
    }

    public CompleteAuthorizeAppWithGithubInput(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String code;
        private String state;

        public Builder() {
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }


        public CompleteAuthorizeAppWithGithubInput build() {
            return new CompleteAuthorizeAppWithGithubInput(code, state);
        }

    }
}
