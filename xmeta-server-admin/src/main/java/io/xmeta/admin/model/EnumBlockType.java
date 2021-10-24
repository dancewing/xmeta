package io.xmeta.admin.model;

public enum EnumBlockType {

    AppSettings("AppSettings"),
    Flow("Flow"),
    ConnectorRestApi("ConnectorRestApi"),
    ConnectorRestApiCall("ConnectorRestApiCall"),
    ConnectorSoapApi("ConnectorSoapApi"),
    ConnectorFile("ConnectorFile"),
    EntityApi("EntityApi"),
    EntityApiEndpoint("EntityApiEndpoint"),
    FlowApi("FlowApi"),
    Layout("Layout"),
    CanvasPage("CanvasPage"),
    EntityPage("EntityPage"),
    Document("Document");

    private final String graphqlName;

    private EnumBlockType(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
