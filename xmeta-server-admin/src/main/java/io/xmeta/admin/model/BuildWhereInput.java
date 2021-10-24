package io.xmeta.admin.model;


public class BuildWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    @javax.validation.constraints.NotNull
    private WhereUniqueInput app;
    private WhereUniqueInput createdBy;
    private StringFilter version;
    private StringFilter message;
    private WhereUniqueInput commit;

    public BuildWhereInput() {
    }

    public BuildWhereInput(StringFilter id, DateTimeFilter createdAt, WhereUniqueInput app, WhereUniqueInput createdBy, StringFilter version, StringFilter message, WhereUniqueInput commit) {
        this.id = id;
        this.createdAt = createdAt;
        this.app = app;
        this.createdBy = createdBy;
        this.version = version;
        this.message = message;
        this.commit = commit;
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

    public WhereUniqueInput getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(WhereUniqueInput createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getVersion() {
        return version;
    }
    public void setVersion(StringFilter version) {
        this.version = version;
    }

    public StringFilter getMessage() {
        return message;
    }
    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public WhereUniqueInput getCommit() {
        return commit;
    }
    public void setCommit(WhereUniqueInput commit) {
        this.commit = commit;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private StringFilter id;
        private DateTimeFilter createdAt;
        private WhereUniqueInput app;
        private WhereUniqueInput createdBy;
        private StringFilter version;
        private StringFilter message;
        private WhereUniqueInput commit;

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

        public Builder setCreatedBy(WhereUniqueInput createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setVersion(StringFilter version) {
            this.version = version;
            return this;
        }

        public Builder setMessage(StringFilter message) {
            this.message = message;
            return this;
        }

        public Builder setCommit(WhereUniqueInput commit) {
            this.commit = commit;
            return this;
        }


        public BuildWhereInput build() {
            return new BuildWhereInput(id, createdAt, app, createdBy, version, message, commit);
        }

    }
}
