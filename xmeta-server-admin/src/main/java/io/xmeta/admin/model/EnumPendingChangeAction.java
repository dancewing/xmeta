package io.xmeta.admin.model;

public enum EnumPendingChangeAction {

    Create("Create"),
    Update("Update"),
    Delete("Delete");

    private final String graphqlName;

    private EnumPendingChangeAction(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
