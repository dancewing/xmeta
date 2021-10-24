package io.xmeta.admin.model;


public class AuthorizeAppWithGithubResult implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String url;

    public AuthorizeAppWithGithubResult() {
    }

    public AuthorizeAppWithGithubResult(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String url;

        public Builder() {
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }


        public AuthorizeAppWithGithubResult build() {
            return new AuthorizeAppWithGithubResult(url);
        }

    }
}
