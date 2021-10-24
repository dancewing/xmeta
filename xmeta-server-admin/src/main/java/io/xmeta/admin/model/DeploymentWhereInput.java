package io.xmeta.admin.model;


public class DeploymentWhereInput implements java.io.Serializable {

    private StringFilter id;
    private DateTimeFilter createdAt;
    @javax.validation.constraints.NotNull
    private WhereUniqueInput build;
    @javax.validation.constraints.NotNull
    private WhereUniqueInput environment;
    private EnumDeploymentStatusFilter status;
    private WhereUniqueInput createdBy;
    private StringFilter message;

    public DeploymentWhereInput() {
    }

    public DeploymentWhereInput(StringFilter id, DateTimeFilter createdAt, WhereUniqueInput build, WhereUniqueInput environment, EnumDeploymentStatusFilter status, WhereUniqueInput createdBy, StringFilter message) {
        this.id = id;
        this.createdAt = createdAt;
        this.build = build;
        this.environment = environment;
        this.status = status;
        this.createdBy = createdBy;
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

    public WhereUniqueInput getBuild() {
        return build;
    }
    public void setBuild(WhereUniqueInput build) {
        this.build = build;
    }

    public WhereUniqueInput getEnvironment() {
        return environment;
    }
    public void setEnvironment(WhereUniqueInput environment) {
        this.environment = environment;
    }

    public EnumDeploymentStatusFilter getStatus() {
        return status;
    }
    public void setStatus(EnumDeploymentStatusFilter status) {
        this.status = status;
    }

    public WhereUniqueInput getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(WhereUniqueInput createdBy) {
        this.createdBy = createdBy;
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
        private WhereUniqueInput build;
        private WhereUniqueInput environment;
        private EnumDeploymentStatusFilter status;
        private WhereUniqueInput createdBy;
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

        public Builder setBuild(WhereUniqueInput build) {
            this.build = build;
            return this;
        }

        public Builder setEnvironment(WhereUniqueInput environment) {
            this.environment = environment;
            return this;
        }

        public Builder setStatus(EnumDeploymentStatusFilter status) {
            this.status = status;
            return this;
        }

        public Builder setCreatedBy(WhereUniqueInput createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setMessage(StringFilter message) {
            this.message = message;
            return this;
        }


        public DeploymentWhereInput build() {
            return new DeploymentWhereInput(id, createdAt, build, environment, status, createdBy, message);
        }

    }
}
