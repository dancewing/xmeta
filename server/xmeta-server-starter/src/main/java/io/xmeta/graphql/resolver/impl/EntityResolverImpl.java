package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.EntityResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityResolverImpl implements EntityResolver {
    @Override
    public List<EntityVersion> versions(Entity entity, EntityVersionWhereInput where, EntityVersionOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }

    @Override
    public List<EntityField> fields(Entity entity, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }
}
