package io.xmeta.admin.model;

public enum EnumConnectorRestApiAuthenticationType {

    None("None"),
    PrivateKey("PrivateKey"),
    HttpBasicAuthentication("HttpBasicAuthentication"),
    OAuth2PasswordFlow("OAuth2PasswordFlow"),
    OAuth2UserAgentFlow("OAuth2UserAgentFlow");

    private final String graphqlName;

    private EnumConnectorRestApiAuthenticationType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
