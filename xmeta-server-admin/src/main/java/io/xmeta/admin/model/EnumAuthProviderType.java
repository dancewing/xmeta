package io.xmeta.admin.model;

public enum EnumAuthProviderType {

    Http("Http"),
    Jwt("Jwt");

    private final String graphqlName;

    private EnumAuthProviderType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
