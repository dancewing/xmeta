package io.xmeta.admin.model;


public class AppUpdateInput implements java.io.Serializable {

    private String name;
    private String description;
    private String color;

    public AppUpdateInput() {
    }

    public AppUpdateInput(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
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

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String description;
        private String color;

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

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }


        public AppUpdateInput build() {
            return new AppUpdateInput(name, description, color);
        }

    }
}
