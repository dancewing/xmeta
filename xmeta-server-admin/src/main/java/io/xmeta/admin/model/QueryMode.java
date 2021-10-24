package io.xmeta.admin.model;

public enum QueryMode {

    Default("Default"),
    Insensitive("Insensitive");

    private final String graphqlName;

    private QueryMode(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
