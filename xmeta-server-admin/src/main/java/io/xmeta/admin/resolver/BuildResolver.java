package io.xmeta.admin.resolver;

import io.xmeta.admin.model.Build;
import io.xmeta.admin.model.Deployment;
import io.xmeta.admin.model.DeploymentOrderByInput;
import io.xmeta.admin.model.DeploymentWhereInput;

/**
 * Resolver for Build
 */
public interface BuildResolver extends graphql.kickstart.tools.GraphQLResolver<Build> {

    @javax.validation.constraints.NotNull
    java.util.List<Deployment> deployments(Build build, DeploymentWhereInput where, DeploymentOrderByInput orderBy, Integer take, Integer skip);

}
