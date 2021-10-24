package io.xmeta.admin.model;

public enum EnumEntityAction {

    View("View"),
    Create("Create"),
    Update("Update"),
    Delete("Delete"),
    Search("Search");

    private final String graphqlName;

    private EnumEntityAction(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
