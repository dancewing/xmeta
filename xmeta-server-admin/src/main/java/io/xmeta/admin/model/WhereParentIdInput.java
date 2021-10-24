package io.xmeta.admin.model;


public class WhereParentIdInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private WhereUniqueInput connect;

    public WhereParentIdInput() {
    }

    public WhereParentIdInput(WhereUniqueInput connect) {
        this.connect = connect;
    }

    public WhereUniqueInput getConnect() {
        return connect;
    }
    public void setConnect(WhereUniqueInput connect) {
        this.connect = connect;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private WhereUniqueInput connect;

        public Builder() {
        }

        public Builder setConnect(WhereUniqueInput connect) {
            this.connect = connect;
            return this;
        }


        public WhereParentIdInput build() {
            return new WhereParentIdInput(connect);
        }

    }
}
