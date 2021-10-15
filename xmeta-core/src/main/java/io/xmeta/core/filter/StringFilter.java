package io.xmeta.core.filter;


public class StringFilter implements java.io.Serializable {

    private String eq;
    private String not;
    private java.util.List<String> in;
    private java.util.List<String> notIn;
    private String lt;
    private String lte;
    private String gt;
    private String gte;
    private String contains;
    private String startsWith;
    private String endsWith;
    private QueryMode mode;

    public StringFilter() {
    }

    public StringFilter(String eq, String not, java.util.List<String> in, java.util.List<String> notIn, String lt, String lte, String gt, String gte, String contains, String startsWith, String endsWith, QueryMode mode) {
        this.eq = eq;
        this.not = not;
        this.in = in;
        this.notIn = notIn;
        this.lt = lt;
        this.lte = lte;
        this.gt = gt;
        this.gte = gte;
        this.contains = contains;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.mode = mode;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public java.util.List<String> getIn() {
        return in;
    }

    public void setIn(java.util.List<String> in) {
        this.in = in;
    }

    public java.util.List<String> getNotIn() {
        return notIn;
    }

    public void setNotIn(java.util.List<String> notIn) {
        this.notIn = notIn;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getLte() {
        return lte;
    }

    public void setLte(String lte) {
        this.lte = lte;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getGte() {
        return gte;
    }

    public void setGte(String gte) {
        this.gte = gte;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public String getEndsWith() {
        return endsWith;
    }

    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }

    public QueryMode getMode() {
        return mode;
    }

    public void setMode(QueryMode mode) {
        this.mode = mode;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String eq;
        private String not;
        private java.util.List<String> in;
        private java.util.List<String> notIn;
        private String lt;
        private String lte;
        private String gt;
        private String gte;
        private String contains;
        private String startsWith;
        private String endsWith;
        private QueryMode mode;

        public Builder() {
        }

        public Builder setEq(String eq) {
            this.eq = eq;
            return this;
        }

        public Builder setNot(String not) {
            this.not = not;
            return this;
        }

        public Builder setIn(java.util.List<String> in) {
            this.in = in;
            return this;
        }

        public Builder setNotIn(java.util.List<String> notIn) {
            this.notIn = notIn;
            return this;
        }

        public Builder setLt(String lt) {
            this.lt = lt;
            return this;
        }

        public Builder setLte(String lte) {
            this.lte = lte;
            return this;
        }

        public Builder setGt(String gt) {
            this.gt = gt;
            return this;
        }

        public Builder setGte(String gte) {
            this.gte = gte;
            return this;
        }

        public Builder setContains(String contains) {
            this.contains = contains;
            return this;
        }

        public Builder setStartsWith(String startsWith) {
            this.startsWith = startsWith;
            return this;
        }

        public Builder setEndsWith(String endsWith) {
            this.endsWith = endsWith;
            return this;
        }

        public Builder setMode(QueryMode mode) {
            this.mode = mode;
            return this;
        }


        public StringFilter build() {
            return new StringFilter(eq, not, in, notIn, lt, lte, gt, gte, contains, startsWith, endsWith, mode);
        }

    }
}
