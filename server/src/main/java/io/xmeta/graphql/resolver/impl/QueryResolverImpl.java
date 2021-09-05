package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.QueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryResolverImpl implements QueryResolver {
    @Override
    public Account account() throws Exception {
        return null;
    }

    @Override
    public List<Workspace> workspaces() throws Exception {
        return null;
    }

    @Override
    public Workspace workspace(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Workspace currentWorkspace() throws Exception {
        return null;
    }

    @Override
    public Entity entity(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public List<Block> blocks(BlockWhereInput where, BlockOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public Block block(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public AppRole appRole(WhereUniqueInput where, Double version) throws Exception {
        return null;
    }

    @Override
    public List<AppRole> appRoles(AppRoleWhereInput where, AppRoleOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public AppSettings appSettings(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<Build> builds(BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) throws Exception {
        return null;
    }

    @Override
    public Build build(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Action action(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<Deployment> deployments(DeploymentWhereInput where, DeploymentOrderByInput orderBy, Integer take, Integer skip) throws Exception {
        return null;
    }

    @Override
    public Deployment deployment(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public App app(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<App> apps(AppWhereInput where, AppOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public List<PendingChange> pendingChanges(PendingChangesFindInput where) throws Exception {
        return null;
    }

    @Override
    public List<GithubRepo> appAvailableGithubRepos(AvailableGithubReposFindInput where) throws Exception {
        return null;
    }

    @Override
    public AppValidationResult appValidateBeforeCommit(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public Commit commit(CommitWhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<Commit> commits(CommitWhereInput where, CommitOrderByInput orderBy, CommitWhereUniqueInput cursor, Integer take, Integer skip) throws Exception {
        return null;
    }

    @Override
    public User me() throws Exception {
        return null;
    }

    @Override
    public List<ApiToken> userApiTokens() throws Exception {
        return null;
    }

    @Override
    public ConnectorRestApi ConnectorRestApi(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<ConnectorRestApi> ConnectorRestApis(ConnectorRestApiWhereInput where, ConnectorRestApiOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public ConnectorRestApiCall ConnectorRestApiCall(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<ConnectorRestApiCall> ConnectorRestApiCalls(ConnectorRestApiCallWhereInput where, ConnectorRestApiCallOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public EntityPage EntityPage(WhereUniqueInput where) throws Exception {
        return null;
    }

    @Override
    public List<EntityPage> EntityPages(EntityPageWhereInput where, EntityPageOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }
}
