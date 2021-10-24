package io.xmeta.admin.model;


public class ActionLog implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private String message;
    @javax.validation.constraints.NotNull
    private java.util.Map<String, Object> meta;
    @javax.validation.constraints.NotNull
    private EnumActionLogLevel level;

    public ActionLog() {
    }

    public ActionLog(String id, java.time.ZonedDateTime createdAt, String message, java.util.Map<String, Object> meta, EnumActionLogLevel level) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
        this.meta = meta;
        this.level = level;
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

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public java.util.Map<String, Object> getMeta() {
        return meta;
    }
    public void setMeta(java.util.Map<String, Object> meta) {
        this.meta = meta;
    }

    public EnumActionLogLevel getLevel() {
        return level;
    }
    public void setLevel(EnumActionLogLevel level) {
        this.level = level;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private String message;
        private java.util.Map<String, Object> meta;
        private EnumActionLogLevel level;

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

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMeta(java.util.Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder setLevel(EnumActionLogLevel level) {
            this.level = level;
            return this;
        }


        public ActionLog build() {
            return new ActionLog(id, createdAt, message, meta, level);
        }

    }
}
