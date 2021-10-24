package io.xmeta.admin.model;


public class EntityVersion implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String id;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime createdAt;
    @javax.validation.constraints.NotNull
    private java.time.ZonedDateTime updatedAt;
    @javax.validation.constraints.NotNull
    private String entityId;
    @javax.validation.constraints.NotNull
    private Entity entity;
    private int versionNumber;
    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private String table;
    @javax.validation.constraints.NotNull
    private String displayName;
    @javax.validation.constraints.NotNull
    private String pluralDisplayName;
    private String description;
    private Commit commit;
    @javax.validation.constraints.NotNull
    private java.util.List<EntityPermission> permissions;

    public EntityVersion() {
    }

    public EntityVersion(String id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, String entityId, Entity entity, int versionNumber, String name, String table, String displayName, String pluralDisplayName, String description, Commit commit, java.util.List<EntityPermission> permissions) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.entityId = entityId;
        this.entity = entity;
        this.versionNumber = versionNumber;
        this.name = name;
        this.table = table;
        this.displayName = displayName;
        this.pluralDisplayName = pluralDisplayName;
        this.description = description;
        this.commit = commit;
        this.permissions = permissions;
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

    public java.time.ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.time.ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Entity getEntity() {
        return entity;
    }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public int getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPluralDisplayName() {
        return pluralDisplayName;
    }
    public void setPluralDisplayName(String pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Commit getCommit() {
        return commit;
    }
    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public java.util.List<EntityPermission> getPermissions() {
        return permissions;
    }
    public void setPermissions(java.util.List<EntityPermission> permissions) {
        this.permissions = permissions;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private java.time.ZonedDateTime createdAt;
        private java.time.ZonedDateTime updatedAt;
        private String entityId;
        private Entity entity;
        private int versionNumber;
        private String name;
        private String table;
        private String displayName;
        private String pluralDisplayName;
        private String description;
        private Commit commit;
        private java.util.List<EntityPermission> permissions;

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

        public Builder setUpdatedAt(java.time.ZonedDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setEntityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setEntity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public Builder setVersionNumber(int versionNumber) {
            this.versionNumber = versionNumber;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTable(String table) {
            this.table = table;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setPluralDisplayName(String pluralDisplayName) {
            this.pluralDisplayName = pluralDisplayName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCommit(Commit commit) {
            this.commit = commit;
            return this;
        }

        public Builder setPermissions(java.util.List<EntityPermission> permissions) {
            this.permissions = permissions;
            return this;
        }


        public EntityVersion build() {
            return new EntityVersion(id, createdAt, updatedAt, entityId, entity, versionNumber, name, table, displayName, pluralDisplayName, description, commit, permissions);
        }

    }
}
