package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.EntityResolver;
import io.xmeta.graphql.service.EntityVersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EntityResolverImpl implements EntityResolver {

    private final EntityVersionService entityVersionService;

    @Override
    public List<EntityVersion> versions(Entity entity, EntityVersionWhereInput where, EntityVersionOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityVersionService.versions(entity, where, orderBy, skip, take);
    }

    @Override
    public List<EntityField> fields(Entity entity, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        return null;
    }
}
