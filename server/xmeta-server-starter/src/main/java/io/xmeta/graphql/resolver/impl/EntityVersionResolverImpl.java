package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.EntityField;
import io.xmeta.graphql.model.EntityFieldOrderByInput;
import io.xmeta.graphql.model.EntityFieldWhereInput;
import io.xmeta.graphql.model.EntityVersion;
import io.xmeta.graphql.resolver.EntityVersionResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityVersionResolverImpl implements EntityVersionResolver {
    @Override
    public List<EntityField> fields(EntityVersion entityVersion, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }
}
