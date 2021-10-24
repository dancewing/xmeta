package io.xmeta.admin.model;

public enum EnumDeploymentStatus {

    Waiting("Waiting"),
    Completed("Completed"),
    Failed("Failed"),
    Removed("Removed");

    private final String graphqlName;

    private EnumDeploymentStatus(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
