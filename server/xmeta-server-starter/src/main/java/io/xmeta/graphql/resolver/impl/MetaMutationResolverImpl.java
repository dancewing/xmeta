package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.MetaMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class MetaMutationResolverImpl implements MetaMutationResolver {
    @Override
    public Account updateAccount(UpdateAccountInput data) throws Exception {
        return null;
    }

    @Override
    public Workspace deleteWorkspace(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Workspace updateWorkspace(WorkspaceUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Workspace createWorkspace(WorkspaceCreateInput data) throws Exception {
        return null;
    }

    @Override
    public User inviteUser(InviteUserInput data) throws Exception {
        return null;
    }

    @Override
    public Entity createOneEntity(EntityCreateInput data) throws Exception {
        return null;
    }

    @Override
    public Entity deleteEntity(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Entity updateEntity(EntityUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Entity lockEntity(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public EntityPermission updateEntityPermission(EntityUpdatePermissionInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public EntityPermission updateEntityPermissionRoles(EntityUpdatePermissionRolesInput data) throws Exception {
        return null;
    }

    @Override
    public EntityPermissionField addEntityPermissionField(EntityAddPermissionFieldInput data) throws Exception {
        return null;
    }

    @Override
    public EntityPermissionField deleteEntityPermissionField(EntityPermissionFieldWhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public EntityPermissionField updateEntityPermissionFieldRoles(EntityUpdatePermissionFieldRolesInput data) throws Exception {
        return null;
    }

    @Override
    public EntityField createEntityField(EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName) throws Exception {
        return null;
    }

    @Override
    public EntityField createEntityFieldByDisplayName(EntityFieldCreateByDisplayNameInput data) throws Exception {
        return null;
    }

    @Override
    public EntityField deleteEntityField(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public EntityField updateEntityField(EntityFieldUpdateInput data, WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName) throws Exception {
        return null;
    }

    @Override
    public EntityField createDefaultRelatedField(WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName) throws Exception {
        return null;
    }

    @Override
    public AppRole createAppRole(AppRoleCreateInput data) throws Exception {
        return null;
    }

    @Override
    public AppRole deleteAppRole(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public AppRole updateAppRole(AppRoleUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public AppSettings updateAppSettings(AppSettingsUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Build createBuild(BuildCreateInput data) throws Exception {
        return null;
    }

    @Override
    public Deployment createDeployment(DeploymentCreateInput data) throws Exception {
        return null;
    }

    @Override
    public App createApp(AppCreateInput data) throws Exception {
        return null;
    }

    @Override
    public App createAppWithEntities(AppCreateWithEntitiesInput data) throws Exception {
        return null;
    }

    @Override
    public App deleteApp(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public App updateApp(AppUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Commit commit(CommitCreateInput data) throws Exception {
        return null;
    }

    @Override
    public Boolean discardPendingChanges(PendingChangesDiscardInput data) throws Exception {
        return null;
    }

    @Override
    public AuthorizeAppWithGithubResult startAuthorizeAppWithGithub(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public App completeAuthorizeAppWithGithub(CompleteAuthorizeAppWithGithubInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public App removeAuthorizeAppWithGithub(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public App appEnableSyncWithGithubRepo(AppEnableSyncWithGithubRepoInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public App appDisableSyncWithGithubRepo(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Auth signup(SignupInput data) throws Exception {
        return null;
    }

    @Override
    public Auth login(LoginInput data) throws Exception {
        return null;
    }

    @Override
    public ApiToken createApiToken(ApiTokenCreateInput data) throws Exception {
        return null;
    }

    @Override
    public Account changePassword(ChangePasswordInput data) throws Exception {
        return null;
    }

    @Override
    public ApiToken deleteApiToken(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Auth setCurrentWorkspace(WhereUniqueInput data) throws Exception {
        return null;
    }

    @Override
    public ConnectorRestApi createConnectorRestApi(ConnectorRestApiCreateInput data) throws Exception {
        return null;
    }

    @Override
    public ConnectorRestApi updateConnectorRestApi(BlockUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public ConnectorRestApiCall createConnectorRestApiCall(ConnectorRestApiCallCreateInput data) throws Exception {
        return null;
    }

    @Override
    public ConnectorRestApiCall updateConnectorRestApiCall(BlockUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public EntityPage createEntityPage(EntityPageCreateInput data) throws Exception {
        return null;
    }

    @Override
    public EntityPage updateEntityPage(EntityPageUpdateInput data, WhereUniqueInput where) throws Exception {
        return null;
    }
}
