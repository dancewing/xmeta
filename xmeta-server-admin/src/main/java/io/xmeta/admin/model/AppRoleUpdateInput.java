package io.xmeta.admin.model;


public class AppRoleUpdateInput implements java.io.Serializable {

    private String name;
    private String description;
    @javax.validation.constraints.NotNull
    private String displayName;

    public AppRoleUpdateInput() {
    }

    public AppRoleUpdateInput(String name, String description, String displayName) {
        this.name = name;
        this.description = description;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String description;
        private String displayName;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }


        public AppRoleUpdateInput build() {
            return new AppRoleUpdateInput(name, description, displayName);
        }

    }
}
