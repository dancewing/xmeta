package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.AppResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppResolverImpl implements AppResolver {
    @Override
    public List<Entity> entities(App app, EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }

    @Override
    public List<Build> builds(App app, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return null;
    }
}
