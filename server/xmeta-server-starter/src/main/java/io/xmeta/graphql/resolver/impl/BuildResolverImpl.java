package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Build;
import io.xmeta.graphql.model.Deployment;
import io.xmeta.graphql.model.DeploymentOrderByInput;
import io.xmeta.graphql.model.DeploymentWhereInput;
import io.xmeta.graphql.resolver.BuildResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildResolverImpl implements BuildResolver {
    @Override
    public List<Deployment> deployments(Build build, DeploymentWhereInput where, DeploymentOrderByInput orderBy, Integer take, Integer skip) {
        return null;
    }
}
