package io.xmeta.core.filter;


public class DateTimeFilter implements java.io.Serializable {

    private java.time.ZonedDateTime eq;
    private java.time.ZonedDateTime not;
    private java.util.List<java.time.ZonedDateTime> in;
    private java.util.List<java.time.ZonedDateTime> notIn;
    private java.time.ZonedDateTime lt;
    private java.time.ZonedDateTime lte;
    private java.time.ZonedDateTime gt;
    private java.time.ZonedDateTime gte;

    public DateTimeFilter() {
    }

    public DateTimeFilter(java.time.ZonedDateTime eq, java.time.ZonedDateTime not, java.util.List<java.time.ZonedDateTime> in, java.util.List<java.time.ZonedDateTime> notIn, java.time.ZonedDateTime lt, java.time.ZonedDateTime lte, java.time.ZonedDateTime gt, java.time.ZonedDateTime gte) {
        this.eq = eq;
        this.not = not;
        this.in = in;
        this.notIn = notIn;
        this.lt = lt;
        this.lte = lte;
        this.gt = gt;
        this.gte = gte;
    }

    public java.time.ZonedDateTime getEq() {
        return eq;
    }

    public void setEq(java.time.ZonedDateTime eq) {
        this.eq = eq;
    }

    public java.time.ZonedDateTime getNot() {
        return not;
    }

    public void setNot(java.time.ZonedDateTime not) {
        this.not = not;
    }

    public java.util.List<java.time.ZonedDateTime> getIn() {
        return in;
    }

    public void setIn(java.util.List<java.time.ZonedDateTime> in) {
        this.in = in;
    }

    public java.util.List<java.time.ZonedDateTime> getNotIn() {
        return notIn;
    }

    public void setNotIn(java.util.List<java.time.ZonedDateTime> notIn) {
        this.notIn = notIn;
    }

    public java.time.ZonedDateTime getLt() {
        return lt;
    }

    public void setLt(java.time.ZonedDateTime lt) {
        this.lt = lt;
    }

    public java.time.ZonedDateTime getLte() {
        return lte;
    }

    public void setLte(java.time.ZonedDateTime lte) {
        this.lte = lte;
    }

    public java.time.ZonedDateTime getGt() {
        return gt;
    }

    public void setGt(java.time.ZonedDateTime gt) {
        this.gt = gt;
    }

    public java.time.ZonedDateTime getGte() {
        return gte;
    }

    public void setGte(java.time.ZonedDateTime gte) {
        this.gte = gte;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private java.time.ZonedDateTime eq;
        private java.time.ZonedDateTime not;
        private java.util.List<java.time.ZonedDateTime> in;
        private java.util.List<java.time.ZonedDateTime> notIn;
        private java.time.ZonedDateTime lt;
        private java.time.ZonedDateTime lte;
        private java.time.ZonedDateTime gt;
        private java.time.ZonedDateTime gte;

        public Builder() {
        }

        public Builder setEq(java.time.ZonedDateTime eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(java.time.ZonedDateTime not) {
            this.not = not;
            return this;
        }

        public Builder setIn(java.util.List<java.time.ZonedDateTime> in) {
            this.in = in;
            return this;
        }

        public Builder setNotIn(java.util.List<java.time.ZonedDateTime> notIn) {
            this.notIn = notIn;
            return this;
        }

        public Builder setLt(java.time.ZonedDateTime lt) {
            this.lt = lt;
            return this;
        }

        public Builder setLte(java.time.ZonedDateTime lte) {
            this.lte = lte;
            return this;
        }

        public Builder setGt(java.time.ZonedDateTime gt) {
            this.gt = gt;
            return this;
        }

        public Builder setGte(java.time.ZonedDateTime gte) {
            this.gte = gte;
            return this;
        }


        public DateTimeFilter build() {
            return new DateTimeFilter(eq, not, in, notIn, lt, lte, gt, gte);
        }

    }
}
