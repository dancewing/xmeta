package io.xmeta.admin.model;

public enum EnumActionLogLevel {

    Error("Error"),
    Warning("Warning"),
    Info("Info"),
    Debug("Debug");

    private final String graphqlName;

    private EnumActionLogLevel(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
