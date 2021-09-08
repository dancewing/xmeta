package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.EntityField;
import io.xmeta.graphql.model.EntityFieldOrderByInput;
import io.xmeta.graphql.model.EntityFieldWhereInput;
import io.xmeta.graphql.model.EntityVersion;
import io.xmeta.graphql.resolver.EntityVersionResolver;
import io.xmeta.graphql.service.EntityFieldService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EntityVersionResolverImpl implements EntityVersionResolver {

    private EntityFieldService entityFieldService;

    @Override
    public List<EntityField> fields(EntityVersion entityVersion, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityFieldService.fields(entityVersion, where, orderBy, skip, take);
    }
}
