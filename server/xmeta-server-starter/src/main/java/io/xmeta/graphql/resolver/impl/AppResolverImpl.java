package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.AppResolver;
import io.xmeta.graphql.service.BuildService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppResolverImpl implements AppResolver {

    private final BuildService buildService;

    @Override
    public List<Entity> entities(App app, EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public List<Build> builds(App app, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return this.buildService.builds(app, where, orderBy, take, skip);
    }
}
