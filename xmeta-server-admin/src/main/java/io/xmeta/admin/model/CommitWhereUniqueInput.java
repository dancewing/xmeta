package io.xmeta.admin.model;


public class CommitWhereUniqueInput implements java.io.Serializable {

    private String id;

    public CommitWhereUniqueInput() {
    }

    public CommitWhereUniqueInput(String id) {
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


        public CommitWhereUniqueInput build() {
            return new CommitWhereUniqueInput(id);
        }

    }
}
