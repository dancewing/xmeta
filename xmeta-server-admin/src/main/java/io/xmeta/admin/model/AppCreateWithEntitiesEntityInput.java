package io.xmeta.admin.model;


public class AppCreateWithEntitiesEntityInput implements java.io.Serializable {

    @javax.validation.constraints.NotNull
    private String name;
    @javax.validation.constraints.NotNull
    private java.util.List<AppCreateWithEntitiesFieldInput> fields;
    private String table;

    public AppCreateWithEntitiesEntityInput() {
    }

    public AppCreateWithEntitiesEntityInput(String name, java.util.List<AppCreateWithEntitiesFieldInput> fields, String table) {
        this.name = name;
        this.fields = fields;
        this.table = table;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<AppCreateWithEntitiesFieldInput> getFields() {
        return fields;
    }
    public void setFields(java.util.List<AppCreateWithEntitiesFieldInput> fields) {
        this.fields = fields;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private java.util.List<AppCreateWithEntitiesFieldInput> fields;
        private String table;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFields(java.util.List<AppCreateWithEntitiesFieldInput> fields) {
            this.fields = fields;
            return this;
        }

        public Builder setTable(String table) {
            this.table = table;
            return this;
        }


        public AppCreateWithEntitiesEntityInput build() {
            return new AppCreateWithEntitiesEntityInput(name, fields, table);
        }

    }
}
