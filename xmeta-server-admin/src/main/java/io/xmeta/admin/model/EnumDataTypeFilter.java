package io.xmeta.admin.model;


public class EnumDataTypeFilter implements java.io.Serializable {

    private EnumDataType eq;
    private EnumDataType not;
    @javax.validation.constraints.NotNull
    private java.util.List<EnumDataType> in;
    @javax.validation.constraints.NotNull
    private java.util.List<EnumDataType> notIn;

    public EnumDataTypeFilter() {
    }

    public EnumDataTypeFilter(EnumDataType eq, EnumDataType not, java.util.List<EnumDataType> in, java.util.List<EnumDataType> notIn) {
        this.eq = eq;
        this.not = not;
        this.in = in;
        this.notIn = notIn;
    }

    public EnumDataType getEq() {
        return eq;
    }
    public void setEq(EnumDataType eq) {
        this.eq = eq;
    }

    public EnumDataType getNot() {
        return not;
    }
    public void setNot(EnumDataType not) {
        this.not = not;
    }

    public java.util.List<EnumDataType> getIn() {
        return in;
    }
    public void setIn(java.util.List<EnumDataType> in) {
        this.in = in;
    }

    public java.util.List<EnumDataType> getNotIn() {
        return notIn;
    }
    public void setNotIn(java.util.List<EnumDataType> notIn) {
        this.notIn = notIn;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EnumDataType eq;
        private EnumDataType not;
        private java.util.List<EnumDataType> in;
        private java.util.List<EnumDataType> notIn;

        public Builder() {
        }

        public Builder setEq(EnumDataType eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(EnumDataType not) {
            this.not = not;
            return this;
        }

        public Builder setIn(java.util.List<EnumDataType> in) {
            this.in = in;
            return this;
        }

        public Builder setNotIn(java.util.List<EnumDataType> notIn) {
            this.notIn = notIn;
            return this;
        }


        public EnumDataTypeFilter build() {
            return new EnumDataTypeFilter(eq, not, in, notIn);
        }

    }
}
