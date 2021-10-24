package io.xmeta.admin.model;


public class WhereUniqueInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;

    public WhereUniqueInput() {
    }

    public WhereUniqueInput(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }


        public WhereUniqueInput build() {
            return new WhereUniqueInput(id);
        }

    }
}
