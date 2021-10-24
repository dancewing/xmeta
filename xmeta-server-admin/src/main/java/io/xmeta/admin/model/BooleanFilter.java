package io.xmeta.admin.model;


public class BooleanFilter implements java.io.Serializable {

    private Boolean eq;
    private Boolean not;

    public BooleanFilter() {
    }

    public BooleanFilter(Boolean eq, Boolean not) {
        this.eq = eq;
        this.not = not;
    }

    public Boolean getEq() {
        return eq;
    }
    public void setEq(Boolean eq) {
        this.eq = eq;
    }

    public Boolean getNot() {
        return not;
    }
    public void setNot(Boolean not) {
        this.not = not;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Boolean eq;
        private Boolean not;

        public Builder() {
        }

        public Builder setEq(Boolean eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(Boolean not) {
            this.not = not;
            return this;
        }


        public BooleanFilter build() {
            return new BooleanFilter(eq, not);
        }

    }
}
