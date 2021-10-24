package io.xmeta.admin.model;


public class WorkspaceUpdateInput implements java.io.Serializable {

    private String name;

    public WorkspaceUpdateInput() {
    }

    public WorkspaceUpdateInput(String name) {
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


        public WorkspaceUpdateInput build() {
            return new WorkspaceUpdateInput(name);
        }

    }
}
