package io.xmeta.admin.resolver;

import io.xmeta.admin.model.*;

public interface MetaQueryResolver extends graphql.kickstart.tools.GraphQLQueryResolver {

    @javax.validation.constraints.NotNull
    Account account();

    @javax.validation.constraints.NotNull
    java.util.List<Workspace> workspaces();

    Workspace workspace(@javax.validation.constraints.NotNull WhereUniqueInput where);

    Workspace currentWorkspace();

    Entity entity(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    java.util.List<Block> blocks(BlockWhereInput where, BlockOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    Block block(@javax.validation.constraints.NotNull WhereUniqueInput where);

    AppRole appRole(@javax.validation.constraints.NotNull WhereUniqueInput where, Double version);

    @javax.validation.constraints.NotNull
    java.util.List<AppRole> appRoles(AppRoleWhereInput where, AppRoleOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    AppSettings appSettings(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<Build> builds(BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip);

    @javax.validation.constraints.NotNull
    Build build(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    Action action(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<Deployment> deployments(DeploymentWhereInput where, DeploymentOrderByInput orderBy, Integer take, Integer skip);

    @javax.validation.constraints.NotNull
    Deployment deployment(@javax.validation.constraints.NotNull WhereUniqueInput where);

    App app(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<App> apps(AppWhereInput where, AppOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    java.util.List<PendingChange> pendingChanges(@javax.validation.constraints.NotNull PendingChangesFindInput where);

    @javax.validation.constraints.NotNull
    java.util.List<GithubRepo> appAvailableGithubRepos(@javax.validation.constraints.NotNull AvailableGithubReposFindInput where);

    @javax.validation.constraints.NotNull
    AppValidationResult appValidateBeforeCommit(@javax.validation.constraints.NotNull WhereUniqueInput where);

    Commit commit(@javax.validation.constraints.NotNull CommitWhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<Commit> commits(CommitWhereInput where, CommitOrderByInput orderBy, CommitWhereUniqueInput cursor, Integer take, Integer skip);

    @javax.validation.constraints.NotNull
    User me();

    @javax.validation.constraints.NotNull
    java.util.List<ApiToken> userApiTokens();

    ConnectorRestApi ConnectorRestApi(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<ConnectorRestApi> ConnectorRestApis(ConnectorRestApiWhereInput where, ConnectorRestApiOrderByInput orderBy, Integer skip, Integer take);

    ConnectorRestApiCall ConnectorRestApiCall(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<ConnectorRestApiCall> ConnectorRestApiCalls(ConnectorRestApiCallWhereInput where, ConnectorRestApiCallOrderByInput orderBy, Integer skip, Integer take);

    EntityPage EntityPage(@javax.validation.constraints.NotNull WhereUniqueInput where);

    @javax.validation.constraints.NotNull
    java.util.List<EntityPage> EntityPages(EntityPageWhereInput where, EntityPageOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    java.util.List<Environment> environments(WhereUniqueInput where);

    Environment environment(@javax.validation.constraints.NotNull WhereUniqueInput where);

}
