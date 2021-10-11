package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.model.Build;
import io.xmeta.admin.model.Deployment;
import io.xmeta.admin.model.DeploymentOrderByInput;
import io.xmeta.admin.model.DeploymentWhereInput;
import io.xmeta.admin.resolver.BuildResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildResolverImpl implements BuildResolver {
    @Override
    public List<Deployment> deployments(Build build, DeploymentWhereInput where, DeploymentOrderByInput orderBy, Integer take, Integer skip) {
        return null;
    }
}
