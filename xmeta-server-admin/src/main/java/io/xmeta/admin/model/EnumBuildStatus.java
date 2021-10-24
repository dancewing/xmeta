package io.xmeta.admin.model;

public enum EnumBuildStatus {

    Running("Running"),
    Completed("Completed"),
    Failed("Failed"),
    Invalid("Invalid");

    private final String graphqlName;

    private EnumBuildStatus(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
