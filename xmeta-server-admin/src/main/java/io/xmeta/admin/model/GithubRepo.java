package io.xmeta.admin.model;


public class GithubRepo implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String url;
    private boolean Private;
    @javax.validation.constraints.NotNull
    private String fullName;
    private boolean admin;

    public GithubRepo() {
    }

    public GithubRepo(String name, String url, boolean Private, String fullName, boolean admin) {
        this.name = name;
        this.url = url;
        this.Private = Private;
        this.fullName = fullName;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getPrivate() {
        return Private;
    }
    public void setPrivate(boolean Private) {
        this.Private = Private;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean getAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String url;
        private boolean Private;
        private String fullName;
        private boolean admin;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setPrivate(boolean Private) {
            this.Private = Private;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setAdmin(boolean admin) {
            this.admin = admin;
            return this;
        }


        public GithubRepo build() {
            return new GithubRepo(name, url, Private, fullName, admin);
        }

    }
}
