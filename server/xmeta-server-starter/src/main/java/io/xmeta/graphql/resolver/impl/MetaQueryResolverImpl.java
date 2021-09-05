package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.MetaQueryResolver;
import io.xmeta.graphql.service.AccountService;
import io.xmeta.graphql.service.AppService;
import io.xmeta.graphql.service.EntityService;
import io.xmeta.graphql.service.WorkspaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MetaQueryResolverImpl implements MetaQueryResolver {

    private final AccountService accountService;

    private final AppService appService;

    private final EntityService entityService;

    private final WorkspaceService workspaceService;

    @Override
    public Account account() {
        return null;
    }

    @Override
    public List<Workspace> workspaces() {
        return null;
    }

    @Override
    public Workspace workspace(WhereUniqueInput where) {
        return null;
    }

    @Override
    public Workspace currentWorkspace() {
        return this.workspaceService.currentWorkspace();
    }

    @Override
    public Entity entity(WhereUniqueInput where) {
        return null;
    }

    @Override
    public List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityService.entities(where, orderBy, skip, take);
    }

    @Override
    public List<Block> blocks(BlockWhereInput where, BlockOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public Block block(WhereUniqueInput where) {
        return null;
    }

    @Override
    public AppRole appRole(WhereUniqueInput where, Double version) {
        return null;
    }

    @Override
    public List<AppRole> appRoles(AppRoleWhereInput where, AppRoleOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public AppSettings appSettings(WhereUniqueInput where) {
        return null;
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
        return null;
    }

    @Override
    public List<GithubRepo> appAvailableGithubRepos(AvailableGithubReposFindInput where) {
        return null;
    }

    @Override
    public AppValidationResult appValidateBeforeCommit(WhereUniqueInput where) {
        return null;
    }

    @Override
    public Commit commit(CommitWhereUniqueInput where) {
        return null;
    }

    @Override
    public List<Commit> commits(CommitWhereInput where, CommitOrderByInput orderBy, CommitWhereUniqueInput cursor, Integer take, Integer skip) {
        return null;
    }

    @Override
    public User me() {
        return null;
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
        return null;
    }

    @Override
    public Build build(WhereUniqueInput where) {
        return null;
    }
}
