package io.xmeta.admin.model;

public enum SortOrder {

    Asc("Asc"),
    Desc("Desc");

    private final String graphqlName;

    private SortOrder(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
