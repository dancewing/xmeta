package io.xmeta.admin.model;

public enum EnumPendingChangeResourceType {

    Entity("Entity"),
    Block("Block");

    private final String graphqlName;

    private EnumPendingChangeResourceType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
