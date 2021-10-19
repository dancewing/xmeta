package io.xmeta.core.domain;

public enum DataType {

    Id("Id"),
    SingleLineText("SingleLineText"),
    MultiLineText("MultiLineText"),
    WholeNumber("WholeNumber"),
    DecimalNumber("DecimalNumber"),
    Date("Date"),
    Time("Time"),
    DateTime("DateTime"),
    Boolean("Boolean"),
    Lookup("Lookup"),
    Email("Email"),
    MultiSelectOptionSet("MultiSelectOptionSet"),
    OptionSet("OptionSet"),
    GeographicLocation("GeographicLocation"),
    CreatedAt("CreatedAt"),
    UpdatedAt("UpdatedAt"),
    CreatedBy("CreatedBy"),
    UpdatedBy("UpdatedBy"),
    Roles("Roles"),
    Username("Username"),
    Password("Password"),
    Json("Json");

    private final String graphqlName;

    DataType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }
}
