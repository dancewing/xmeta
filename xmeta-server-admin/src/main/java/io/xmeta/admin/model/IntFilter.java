package io.xmeta.admin.model;


public class IntFilter implements java.io.Serializable {

    private Integer eq;
    private Integer not;
    private java.util.List<Integer> in;
    private java.util.List<Integer> notIn;
    private Integer lt;
    private Integer lte;
    private Integer gt;
    private Integer gte;

    public IntFilter() {
    }

    public IntFilter(Integer eq, Integer not, java.util.List<Integer> in, java.util.List<Integer> notIn, Integer lt, Integer lte, Integer gt, Integer gte) {
        this.eq = eq;
        this.not = not;
        this.in = in;
        this.notIn = notIn;
        this.lt = lt;
        this.lte = lte;
        this.gt = gt;
        this.gte = gte;
    }

    public Integer getEq() {
        return eq;
    }
    public void setEq(Integer eq) {
        this.eq = eq;
    }

    public Integer getNot() {
        return not;
    }
    public void setNot(Integer not) {
        this.not = not;
    }

    public java.util.List<Integer> getIn() {
        return in;
    }
    public void setIn(java.util.List<Integer> in) {
        this.in = in;
    }

    public java.util.List<Integer> getNotIn() {
        return notIn;
    }
    public void setNotIn(java.util.List<Integer> notIn) {
        this.notIn = notIn;
    }

    public Integer getLt() {
        return lt;
    }
    public void setLt(Integer lt) {
        this.lt = lt;
    }

    public Integer getLte() {
        return lte;
    }
    public void setLte(Integer lte) {
        this.lte = lte;
    }

    public Integer getGt() {
        return gt;
    }
    public void setGt(Integer gt) {
        this.gt = gt;
    }

    public Integer getGte() {
        return gte;
    }
    public void setGte(Integer gte) {
        this.gte = gte;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Integer eq;
        private Integer not;
        private java.util.List<Integer> in;
        private java.util.List<Integer> notIn;
        private Integer lt;
        private Integer lte;
        private Integer gt;
        private Integer gte;

        public Builder() {
        }

        public Builder setEq(Integer eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(Integer not) {
            this.not = not;
            return this;
        }

        public Builder setIn(java.util.List<Integer> in) {
            this.in = in;
            return this;
        }

        public Builder setNotIn(java.util.List<Integer> notIn) {
            this.notIn = notIn;
            return this;
        }

        public Builder setLt(Integer lt) {
            this.lt = lt;
            return this;
        }

        public Builder setLte(Integer lte) {
            this.lte = lte;
            return this;
        }

        public Builder setGt(Integer gt) {
            this.gt = gt;
            return this;
        }

        public Builder setGte(Integer gte) {
            this.gte = gte;
            return this;
        }


        public IntFilter build() {
            return new IntFilter(eq, not, in, notIn, lt, lte, gt, gte);
        }

    }
}
