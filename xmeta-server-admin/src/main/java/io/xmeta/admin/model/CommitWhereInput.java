package io.xmeta.admin.model;


public class CommitWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    @javax.validation.constraints.NotNull
    private WhereUniqueInput app;
    private WhereUniqueInput user;
    private StringFilter message;

    public CommitWhereInput() {
    }

    public CommitWhereInput(StringFilter id, DateTimeFilter createdAt, WhereUniqueInput app, WhereUniqueInput user, StringFilter message) {
        this.id = id;
        this.createdAt = createdAt;
        this.app = app;
        this.user = user;
        this.message = message;
    }

    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }

    public DateTimeFilter getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(DateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }

    public WhereUniqueInput getApp() {
        return app;
    }
    public void setApp(WhereUniqueInput app) {
        this.app = app;
    }

    public WhereUniqueInput getUser() {
        return user;
    }
    public void setUser(WhereUniqueInput user) {
        this.user = user;
    }

    public StringFilter getMessage() {
        return message;
    }
    public void setMessage(StringFilter message) {
        this.message = message;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private DateTimeFilter createdAt;
        private WhereUniqueInput app;
        private WhereUniqueInput user;
        private StringFilter message;

        public Builder() {
        }

        public Builder setId(StringFilter id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(DateTimeFilter createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setApp(WhereUniqueInput app) {
            this.app = app;
            return this;
        }

        public Builder setUser(WhereUniqueInput user) {
            this.user = user;
            return this;
        }

        public Builder setMessage(StringFilter message) {
            this.message = message;
            return this;
        }


        public CommitWhereInput build() {
            return new CommitWhereInput(id, createdAt, app, user, message);
        }

    }
}
