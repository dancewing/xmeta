package io.xmeta.admin.model;

public enum EnumDatabaseType {

    MySQL("MySQL"),
    Oracle("Oracle");

    private final String graphqlName;

    private EnumDatabaseType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
