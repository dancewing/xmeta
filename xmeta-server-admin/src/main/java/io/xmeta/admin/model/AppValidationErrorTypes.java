package io.xmeta.admin.model;

public enum AppValidationErrorTypes {

    CannotMergeCodeToGitHubBreakingChanges("CannotMergeCodeToGitHubBreakingChanges"),
    CannotMergeCodeToGitHubInvalidAppId("CannotMergeCodeToGitHubInvalidAppId"),
    DataServiceGeneratorVersionMissing("DataServiceGeneratorVersionMissing"),
    DataServiceGeneratorVersionInvalid("DataServiceGeneratorVersionInvalid");

    private final String graphqlName;

    private AppValidationErrorTypes(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
