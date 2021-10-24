package io.xmeta.admin.resolver;

import io.xmeta.admin.model.*;

public interface MetaMutationResolver extends graphql.kickstart.tools.GraphQLMutationResolver {

    @javax.validation.constraints.NotNull
    Account updateAccount(@javax.validation.constraints.NotNull UpdateAccountInput data);

    Workspace deleteWorkspace(@javax.validation.constraints.NotNull WhereUniqueInput where);

    Workspace updateWorkspace(@javax.validation.constraints.NotNull WorkspaceUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    Workspace createWorkspace(@javax.validation.constraints.NotNull WorkspaceCreateInput data);

    User inviteUser(@javax.validation.constraints.NotNull InviteUserInput data);

    @javax.validation.constraints.NotNull
    Entity createOneEntity(@javax.validation.constraints.NotNull EntityCreateInput data);

    Entity deleteEntity(@javax.validation.constraints.NotNull WhereUniqueInput where);

    Entity updateEntity(@javax.validation.constraints.NotNull EntityUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    Entity lockEntity(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    EntityPermission updateEntityPermission(@javax.validation.constraints.NotNull EntityUpdatePermissionInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    EntityPermission updateEntityPermissionRoles(@javax.validation.constraints.NotNull EntityUpdatePermissionRolesInput data);

    @javax.validation.constraints.NotNull
    EntityPermissionField addEntityPermissionField(@javax.validation.constraints.NotNull EntityAddPermissionFieldInput data);

    @javax.validation.constraints.NotNull
    EntityPermissionField deleteEntityPermissionField(@javax.validation.constraints.NotNull EntityPermissionFieldWhereUniqueInput where);

    @javax.validation.constraints.NotNull
    EntityPermissionField updateEntityPermissionFieldRoles(@javax.validation.constraints.NotNull EntityUpdatePermissionFieldRolesInput data);

    @javax.validation.constraints.NotNull
    EntityField createEntityField(@javax.validation.constraints.NotNull EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName);

    @javax.validation.constraints.NotNull
    EntityField createEntityFieldByDisplayName(@javax.validation.constraints.NotNull EntityFieldCreateByDisplayNameInput data);

    @javax.validation.constraints.NotNull
    EntityField deleteEntityField(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    EntityField updateEntityField(@javax.validation.constraints.NotNull EntityFieldUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName);

    @javax.validation.constraints.NotNull
    EntityField createDefaultRelatedField(@javax.validation.constraints.NotNull WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName);

    @javax.validation.constraints.NotNull
    AppRole createAppRole(@javax.validation.constraints.NotNull AppRoleCreateInput data);

    AppRole deleteAppRole(@javax.validation.constraints.NotNull WhereUniqueInput where);

    AppRole updateAppRole(@javax.validation.constraints.NotNull AppRoleUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    AppSettings updateAppSettings(@javax.validation.constraints.NotNull AppSettingsUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    Build createBuild(@javax.validation.constraints.NotNull BuildCreateInput data);

    @javax.validation.constraints.NotNull
    Deployment createDeployment(@javax.validation.constraints.NotNull DeploymentCreateInput data);

    @javax.validation.constraints.NotNull
    App createApp(@javax.validation.constraints.NotNull AppCreateInput data);

    @javax.validation.constraints.NotNull
    App createAppWithEntities(@javax.validation.constraints.NotNull AppCreateWithEntitiesInput data);

    App deleteApp(@javax.validation.constraints.NotNull WhereUniqueInput where);

    App updateApp(@javax.validation.constraints.NotNull AppUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    Commit commit(@javax.validation.constraints.NotNull CommitCreateInput data);

    Boolean discardPendingChanges(@javax.validation.constraints.NotNull PendingChangesDiscardInput data);

    @javax.validation.constraints.NotNull
    AuthorizeAppWithGithubResult startAuthorizeAppWithGithub(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    App completeAuthorizeAppWithGithub(@javax.validation.constraints.NotNull CompleteAuthorizeAppWithGithubInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    App removeAuthorizeAppWithGithub(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    App appEnableSyncWithGithubRepo(@javax.validation.constraints.NotNull AppEnableSyncWithGithubRepoInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    App appDisableSyncWithGithubRepo(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    Auth signup(@javax.validation.constraints.NotNull SignupInput data);

    @javax.validation.constraints.NotNull
    Auth login(@javax.validation.constraints.NotNull LoginInput data);

    @javax.validation.constraints.NotNull
    ApiToken createApiToken(@javax.validation.constraints.NotNull ApiTokenCreateInput data);

    @javax.validation.constraints.NotNull
    Account changePassword(@javax.validation.constraints.NotNull ChangePasswordInput data);

    @javax.validation.constraints.NotNull
    ApiToken deleteApiToken(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    Auth setCurrentWorkspace(@javax.validation.constraints.NotNull WhereUniqueInput data);

    @javax.validation.constraints.NotNull
    ConnectorRestApi createConnectorRestApi(@javax.validation.constraints.NotNull ConnectorRestApiCreateInput data);

    @javax.validation.constraints.NotNull
    ConnectorRestApi updateConnectorRestApi(@javax.validation.constraints.NotNull BlockUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    ConnectorRestApiCall createConnectorRestApiCall(@javax.validation.constraints.NotNull ConnectorRestApiCallCreateInput data);

    @javax.validation.constraints.NotNull
    ConnectorRestApiCall updateConnectorRestApiCall(@javax.validation.constraints.NotNull BlockUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    EntityPage createEntityPage(@javax.validation.constraints.NotNull EntityPageCreateInput data);

    @javax.validation.constraints.NotNull
    EntityPage updateEntityPage(@javax.validation.constraints.NotNull EntityPageUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    Environment createEnvironment(@javax.validation.constraints.NotNull EnvironmentCreateInput data);

    @javax.validation.constraints.NotNull
    Environment updateEnvironment(@javax.validation.constraints.NotNull EnvironmentUpdateInput data, @javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    Environment deleteEnvironment(@javax.validation.constraints.NotNull WhereUniqueInput where);

}
