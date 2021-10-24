package io.xmeta.admin.model;

public enum EnumActionStepStatus {

    Waiting("Waiting"),
    Running("Running"),
    Failed("Failed"),
    Success("Success");

    private final String graphqlName;

    private EnumActionStepStatus(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
