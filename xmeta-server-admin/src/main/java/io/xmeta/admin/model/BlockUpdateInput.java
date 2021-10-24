package io.xmeta.admin.model;


public class BlockUpdateInput implements java.io.Serializable {

    private String displayName;
    private String description;

    public BlockUpdateInput() {
    }

    public BlockUpdateInput(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String displayName;
        private String description;

        public Builder() {
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }


        public BlockUpdateInput build() {
            return new BlockUpdateInput(displayName, description);
        }

    }
}
