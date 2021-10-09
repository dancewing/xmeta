package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.service.*;
import io.xmeta.admin.model.*;
import io.xmeta.admin.resolver.MetaQueryResolver;
import io.xmeta.admin.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MetaQueryResolverImpl implements MetaQueryResolver {

    private final AccountService accountService;

    private final AppService appService;

    private final AppRoleService appRoleService;

    private final EntityService entityService;

    private final WorkspaceService workspaceService;

    private final CommitService commitService;

    private final AuthService authService;

    private final BuildService buildService;

    private final BlockService blockService;

    @Override
    public Account account() {
        return this.accountService.currentAccount();
    }

    @Override
    public List<Workspace> workspaces() {
        return this.workspaceService.getCurrentAccountWorkspaces();
    }

    @Override
    public Workspace workspace(WhereUniqueInput where) {
        return this.workspaceService.getWorkspace(where.getId());
    }

    @Override
    public Workspace currentWorkspace() {
        return this.workspaceService.currentWorkspace();
    }

    @Override
    public Entity entity(WhereUniqueInput where) {
        return this.entityService.getEntity(where.getId());
    }

    @Override
    public List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityService.entities(where, orderBy, skip, take);
    }

    @Override
    public List<Block> blocks(BlockWhereInput where, BlockOrderByInput orderBy, Integer skip, Integer take) {
        return this.blockService.blocks(where, orderBy, skip, take);
    }

    @Override
    public Block block(WhereUniqueInput where) {
        return this.blockService.getBlock(where.getId());
    }

    @Override
    public AppRole appRole(WhereUniqueInput where, Double version) {
        return this.appRoleService.appRole(where, version);
    }

    @Override
    public List<AppRole> appRoles(AppRoleWhereInput where, AppRoleOrderByInput orderBy, Integer skip, Integer take) {
        return this.appRoleService.appRoles(where, orderBy, skip, take);
    }

    @Override
    public AppSettings appSettings(WhereUniqueInput where) {
        return this.appService.appSettings(where.getId());
    }

    @Override
    public Action action(WhereUniqueInput where) {
        return null;
    }

    @Override
    public List<Deployment> deployments(DeploymentWhereInput where, DeploymentOrderByInput orderBy, Integer take, Integer skip) {
        return null;
    }

    @Override
    public Deployment deployment(WhereUniqueInput where) {
        return null;
    }

    @Override
    public App app(WhereUniqueInput where) {
        return this.appService.app(where);
    }

    @Override
    public List<App> apps(AppWhereInput where, AppOrderByInput orderBy, Integer skip, Integer take) {
        return this.appService.apps(where, orderBy, skip, take);
    }

    @Override
    public List<PendingChange> pendingChanges(PendingChangesFindInput where) {
        return this.appService.pendingChanges(where);
    }

    @Override
    public List<GithubRepo> appAvailableGithubRepos(AvailableGithubReposFindInput where) {
        return null;
    }

    @Override
    public AppValidationResult appValidateBeforeCommit(WhereUniqueInput where) {
        return this.appService.appValidateBeforeCommit(where);
    }

    @Override
    public Commit commit(CommitWhereUniqueInput where) {
        return this.commitService.getCommit(where.getId());
    }

    @Override
    public List<Commit> commits(CommitWhereInput where, CommitOrderByInput orderBy, CommitWhereUniqueInput cursor, Integer take, Integer skip) {
        return this.commitService.commits(where, orderBy, cursor, take, skip);
    }

    @Override
    public User me() {
        return this.authService.currentUser();
    }

    @Override
    public List<ApiToken> userApiTokens() {
        return null;
    }

    @Override
    public ConnectorRestApi ConnectorRestApi(WhereUniqueInput where) {
        return null;
    }

    @Override
    public List<ConnectorRestApi> ConnectorRestApis(ConnectorRestApiWhereInput where, ConnectorRestApiOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public ConnectorRestApiCall ConnectorRestApiCall(WhereUniqueInput where) {
        return null;
    }

    @Override
    public List<ConnectorRestApiCall> ConnectorRestApiCalls(ConnectorRestApiCallWhereInput where, ConnectorRestApiCallOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public EntityPage EntityPage(WhereUniqueInput where) {
        return null;
    }

    @Override
    public List<EntityPage> EntityPages(EntityPageWhereInput where, EntityPageOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public List<Build> builds(BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return this.buildService.builds(null, null, where, orderBy, take, skip);
    }

    @Override
    public Build build(WhereUniqueInput where) {
        return this.buildService.getBuild(where.getId());
    }
}
