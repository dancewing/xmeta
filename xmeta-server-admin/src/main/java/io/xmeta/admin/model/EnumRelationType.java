package io.xmeta.admin.model;

public enum EnumRelationType {

    manyToOne("manyToOne"),
    oneWay("oneWay"),
    oneToMany("oneToMany"),
    manyToMany("manyToMany");

    private final String graphqlName;

    private EnumRelationType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
