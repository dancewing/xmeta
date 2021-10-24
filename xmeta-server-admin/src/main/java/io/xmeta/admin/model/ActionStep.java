package io.xmeta.admin.model;


public class ActionStep implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private EnumActionStepStatus status;
    private java.time.ZonedDateTime completedAt;
    @javax.validation.constraints.NotNull
    private java.util.List<ActionLog> logs;

    public ActionStep() {
    }

    public ActionStep(String id, java.time.ZonedDateTime createdAt, String name, String message, EnumActionStepStatus status, java.time.ZonedDateTime completedAt, java.util.List<ActionLog> logs) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.message = message;
        this.status = status;
        this.completedAt = completedAt;
        this.logs = logs;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public EnumActionStepStatus getStatus() {
        return status;
    }
    public void setStatus(EnumActionStepStatus status) {
        this.status = status;
    }

    public java.time.ZonedDateTime getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(java.time.ZonedDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public java.util.List<ActionLog> getLogs() {
        return logs;
    }
    public void setLogs(java.util.List<ActionLog> logs) {
        this.logs = logs;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private String name;
        private String message;
        private EnumActionStepStatus status;
        private java.time.ZonedDateTime completedAt;
        private java.util.List<ActionLog> logs;

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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setStatus(EnumActionStepStatus status) {
            this.status = status;
            return this;
        }

        public Builder setCompletedAt(java.time.ZonedDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder setLogs(java.util.List<ActionLog> logs) {
            this.logs = logs;
            return this;
        }


        public ActionStep build() {
            return new ActionStep(id, createdAt, name, message, status, completedAt, logs);
        }

    }
}
