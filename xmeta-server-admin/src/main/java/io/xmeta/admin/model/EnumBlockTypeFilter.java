package io.xmeta.admin.model;


public class EnumBlockTypeFilter implements java.io.Serializable {

    private EnumBlockType eq;
    private EnumBlockType not;
    @javax.validation.constraints.NotNull
    private java.util.List<EnumBlockType> in;
    @javax.validation.constraints.NotNull
    private java.util.List<EnumBlockType> notIn;

    public EnumBlockTypeFilter() {
    }

    public EnumBlockTypeFilter(EnumBlockType eq, EnumBlockType not, java.util.List<EnumBlockType> in, java.util.List<EnumBlockType> notIn) {
        this.eq = eq;
        this.not = not;
        this.in = in;
        this.notIn = notIn;
    }

    public EnumBlockType getEq() {
        return eq;
    }
    public void setEq(EnumBlockType eq) {
        this.eq = eq;
    }

    public EnumBlockType getNot() {
        return not;
    }
    public void setNot(EnumBlockType not) {
        this.not = not;
    }

    public java.util.List<EnumBlockType> getIn() {
        return in;
    }
    public void setIn(java.util.List<EnumBlockType> in) {
        this.in = in;
    }

    public java.util.List<EnumBlockType> getNotIn() {
        return notIn;
    }
    public void setNotIn(java.util.List<EnumBlockType> notIn) {
        this.notIn = notIn;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumBlockType eq;
        private EnumBlockType not;
        private java.util.List<EnumBlockType> in;
        private java.util.List<EnumBlockType> notIn;

        public Builder() {
        }

        public Builder setEq(EnumBlockType eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(EnumBlockType not) {
            this.not = not;
            return this;
        }

        public Builder setIn(java.util.List<EnumBlockType> in) {
            this.in = in;
            return this;
        }

        public Builder setNotIn(java.util.List<EnumBlockType> notIn) {
            this.notIn = notIn;
            return this;
        }


        public EnumBlockTypeFilter build() {
            return new EnumBlockTypeFilter(eq, not, in, notIn);
        }

    }
}
