package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.service.*;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.MetaMutationResolver;
import io.xmeta.graphql.service.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MetaMutationResolverImpl implements MetaMutationResolver {

    private final AccountService accountService;
    private final AppService appService;
    private final AppRoleService appRoleService;
    private final CommitService commitService;
    private final AuthService authService;
    private final WorkspaceService workspaceService;
    private final EntityService entityService;
    private final EntityFieldService entityFieldService;
    private final EntityPermissionService entityPermissionService;
    private final EntityPermissionFieldService entityPermissionFieldService;

    @Override
    public Account updateAccount(UpdateAccountInput data) {
        return null;
    }

    @Override
    public Workspace deleteWorkspace(WhereUniqueInput where) {
        return null;
    }

    @Override
    public Workspace updateWorkspace(WorkspaceUpdateInput data, WhereUniqueInput where) {
        return null;
    }

    @Override
    public Workspace createWorkspace(WorkspaceCreateInput data) {
        return this.workspaceService.createWorkspace(data);
    }

    @Override
    public User inviteUser(InviteUserInput data) {
        return null;
    }

    @Override
    public Entity createOneEntity(EntityCreateInput data) {
        return this.entityService.createOneEntity(data);
    }

    @Override
    public Entity deleteEntity(WhereUniqueInput where) {
        return this.entityService.deleteEntity(where);
    }

    @Override
    public Entity updateEntity(EntityUpdateInput data, WhereUniqueInput where) {
        return this.entityService.updateEntity(data, where);
    }

    @Override
    public Entity lockEntity(WhereUniqueInput where) {
        return this.entityService.lockEntity(where);
    }

    @Override
    public EntityPermission updateEntityPermission(EntityUpdatePermissionInput data, WhereUniqueInput where) {
        return this.entityPermissionService.updateEntityPermission(data, where);
    }

    @Override
    public EntityPermission updateEntityPermissionRoles(EntityUpdatePermissionRolesInput data) {
        return this.entityPermissionService.updateEntityPermissionRoles(data);
    }

    @Override
    public EntityPermissionField addEntityPermissionField(EntityAddPermissionFieldInput data) {
        return this.entityPermissionFieldService.addEntityPermissionField(data);
    }

    @Override
    public EntityPermissionField deleteEntityPermissionField(EntityPermissionFieldWhereUniqueInput where) {
        return this.entityPermissionFieldService.deleteEntityPermissionField(where);
    }

    @Override
    public EntityPermissionField updateEntityPermissionFieldRoles(EntityUpdatePermissionFieldRolesInput data) {
        return this.entityPermissionFieldService.updateEntityPermissionFieldRoles(data);
    }

    @Override
    public EntityField createEntityField(EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName) {
        return this.entityFieldService.createEntityField(data, relatedFieldDisplayName, relatedFieldDisplayName);
    }

    @Override
    public EntityField createEntityFieldByDisplayName(EntityFieldCreateByDisplayNameInput data) {
        return this.entityFieldService.createEntityFieldByDisplayName(data);
    }

    @Override
    public EntityField deleteEntityField(WhereUniqueInput where) {
        return this.entityFieldService.deleteEntityField(where.getId());
    }

    @Override
    public EntityField updateEntityField(EntityFieldUpdateInput data, WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName) {
        return this.entityFieldService.updateEntityField(data, where, relatedFieldName, relatedFieldDisplayName);
    }

    @Override
    public EntityField createDefaultRelatedField(WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName) {
        return null;
    }

    @Override
    public AppRole createAppRole(AppRoleCreateInput data) {
        return this.appRoleService.createAppRole(data);
    }

    @Override
    public AppRole deleteAppRole(WhereUniqueInput where) {
        return this.appRoleService.deleteAppRole(where);
    }

    @Override
    public AppRole updateAppRole(AppRoleUpdateInput data, WhereUniqueInput where) {
        return this.appRoleService.updateAppRole(data, where);
    }

    @Override
    public AppSettings updateAppSettings(AppSettingsUpdateInput data, WhereUniqueInput where) {
        return this.appService.updateAppSettings(data, where);
    }

    @Override
    public Deployment createDeployment(DeploymentCreateInput data) {
        return null;
    }

    @Override
    public App createApp(AppCreateInput data) {
        return this.appService.createApp(data);
    }

    @Override
    public App createAppWithEntities(AppCreateWithEntitiesInput data) {
        return this.appService.createAppWithEntities(data);
    }

    @Override
    public App deleteApp(WhereUniqueInput where) {
        return this.appService.deleteApp(where);
    }

    @Override
    public App updateApp(AppUpdateInput data, WhereUniqueInput where) {
        return this.appService.updateApp(data, where);
    }

    @Override
    public Commit commit(CommitCreateInput data) {
        return this.appService.commit(data, false);
    }

    @Override
    public Boolean discardPendingChanges(PendingChangesDiscardInput data) {
        return this.appService.discardPendingChanges(data);
    }

    @Override
    public AuthorizeAppWithGithubResult startAuthorizeAppWithGithub(WhereUniqueInput where) {
        return null;
    }

    @Override
    public App completeAuthorizeAppWithGithub(CompleteAuthorizeAppWithGithubInput data, WhereUniqueInput where) {
        return null;
    }

    @Override
    public App removeAuthorizeAppWithGithub(WhereUniqueInput where) {
        return null;
    }

    @Override
    public App appEnableSyncWithGithubRepo(AppEnableSyncWithGithubRepoInput data, WhereUniqueInput where) {
        return null;
    }

    @Override
    public App appDisableSyncWithGithubRepo(WhereUniqueInput where) {
        return null;
    }

    @Override
    public Auth signup(SignupInput data) {
        data.setEmail(StringUtils.lowerCase(data.getEmail()));
        return this.authService.signup(data);
    }

    @Override
    public Auth login(LoginInput data) {
        return this.authService.login(data);
    }

    @Override
    public ApiToken createApiToken(ApiTokenCreateInput data) {
        return null;
    }

    @Override
    public Account changePassword(ChangePasswordInput data) {
        return null;
    }

    @Override
    public ApiToken deleteApiToken(WhereUniqueInput where) {
        return null;
    }

    @Override
    public Auth setCurrentWorkspace(WhereUniqueInput data) {
        return this.authService.setCurrentWorkspace(data);
    }

    @Override
    public ConnectorRestApi createConnectorRestApi(ConnectorRestApiCreateInput data) {
        return null;
    }

    @Override
    public ConnectorRestApi updateConnectorRestApi(BlockUpdateInput data, WhereUniqueInput where) {
        return null;
    }

    @Override
    public ConnectorRestApiCall createConnectorRestApiCall(ConnectorRestApiCallCreateInput data) {
        return null;
    }

    @Override
    public ConnectorRestApiCall updateConnectorRestApiCall(BlockUpdateInput data, WhereUniqueInput where) {
        return null;
    }

    @Override
    public EntityPage createEntityPage(EntityPageCreateInput data) {
        return null;
    }

    @Override
    public EntityPage updateEntityPage(EntityPageUpdateInput data, WhereUniqueInput where) {
        return null;
    }

    @Override
    public Build createBuild(BuildCreateInput data) {
        return null;
    }
}
