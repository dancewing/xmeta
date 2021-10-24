package io.xmeta.admin.model;

public enum EnumEntityPermissionType {

    AllRoles("AllRoles"),
    Granular("Granular"),
    Disabled("Disabled");

    private final String graphqlName;

    private EnumEntityPermissionType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
