package io.xmeta.admin.model;


public class Action implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.util.List<ActionStep> steps;

    public Action() {
    }

    public Action(String id, java.time.ZonedDateTime createdAt, java.util.List<ActionStep> steps) {
        this.id = id;
        this.createdAt = createdAt;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public java.time.ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.time.ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.List<ActionStep> getSteps() {
        return steps;
    }
    public void setSteps(java.util.List<ActionStep> steps) {
        this.steps = steps;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.util.List<ActionStep> steps;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(java.time.ZonedDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setSteps(java.util.List<ActionStep> steps) {
            this.steps = steps;
            return this;
        }


        public Action build() {
            return new Action(id, createdAt, steps);
        }

    }
}
