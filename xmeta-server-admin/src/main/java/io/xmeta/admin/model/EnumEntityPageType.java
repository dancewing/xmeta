package io.xmeta.admin.model;

public enum EnumEntityPageType {

    SingleRecord("SingleRecord"),
    List("List"),
    MasterDetails("MasterDetails");

    private final String graphqlName;

    private EnumEntityPageType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
