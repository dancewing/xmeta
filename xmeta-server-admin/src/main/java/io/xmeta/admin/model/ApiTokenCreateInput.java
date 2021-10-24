package io.xmeta.admin.model;


public class ApiTokenCreateInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;

    public ApiTokenCreateInput() {
    }

    public ApiTokenCreateInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public ApiTokenCreateInput build() {
            return new ApiTokenCreateInput(name);
        }

    }
}
