package io.xmeta.admin.model;


public class EnumDeploymentStatusFilter implements java.io.Serializable {

    private EnumDeploymentStatus eq;
    private EnumDeploymentStatus not;
    @javax.validation.constraints.NotNull
    private java.util.List<EnumDeploymentStatus> in;
    @javax.validation.constraints.NotNull
    private java.util.List<EnumDeploymentStatus> notIn;

    public EnumDeploymentStatusFilter() {
    }

    public EnumDeploymentStatusFilter(EnumDeploymentStatus eq, EnumDeploymentStatus not, java.util.List<EnumDeploymentStatus> in, java.util.List<EnumDeploymentStatus> notIn) {
        this.eq = eq;
        this.not = not;
        this.in = in;
        this.notIn = notIn;
    }

    public EnumDeploymentStatus getEq() {
        return eq;
    }
    public void setEq(EnumDeploymentStatus eq) {
        this.eq = eq;
    }

    public EnumDeploymentStatus getNot() {
        return not;
    }
    public void setNot(EnumDeploymentStatus not) {
        this.not = not;
    }

    public java.util.List<EnumDeploymentStatus> getIn() {
        return in;
    }
    public void setIn(java.util.List<EnumDeploymentStatus> in) {
        this.in = in;
    }

    public java.util.List<EnumDeploymentStatus> getNotIn() {
        return notIn;
    }
    public void setNotIn(java.util.List<EnumDeploymentStatus> notIn) {
        this.notIn = notIn;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumDeploymentStatus eq;
        private EnumDeploymentStatus not;
        private java.util.List<EnumDeploymentStatus> in;
        private java.util.List<EnumDeploymentStatus> notIn;

        public Builder() {
        }

        public Builder setEq(EnumDeploymentStatus eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(EnumDeploymentStatus not) {
            this.not = not;
            return this;
        }

        public Builder setIn(java.util.List<EnumDeploymentStatus> in) {
            this.in = in;
            return this;
        }

        public Builder setNotIn(java.util.List<EnumDeploymentStatus> notIn) {
            this.notIn = notIn;
            return this;
        }


        public EnumDeploymentStatusFilter build() {
            return new EnumDeploymentStatusFilter(eq, not, in, notIn);
        }

    }
}
