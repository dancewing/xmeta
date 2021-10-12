package io.xmeta.core.domain;

public enum DataType {

    Id("Id"),
    SingleLineText("SingleLineText"),
    MultiLineText("MultiLineText"),
    Email("Email"),
    WholeNumber("WholeNumber"),
    DateTime("DateTime"),
    DecimalNumber("DecimalNumber"),
    Lookup("Lookup"),
    MultiSelectOptionSet("MultiSelectOptionSet"),
    OptionSet("OptionSet"),
    Boolean("Boolean"),
    GeographicLocation("GeographicLocation"),
    CreatedAt("CreatedAt"),
    UpdatedAt("UpdatedAt"),
    Roles("Roles"),
    Username("Username"),
    Password("Password"),
    Json("Json");

    private final String graphqlName;

    private DataType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }
}
