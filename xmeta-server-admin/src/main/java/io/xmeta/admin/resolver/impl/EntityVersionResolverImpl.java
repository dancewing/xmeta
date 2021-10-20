package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.model.EntityField;
import io.xmeta.admin.model.EntityFieldOrderByInput;
import io.xmeta.admin.model.EntityFieldWhereInput;
import io.xmeta.admin.model.EntityVersion;
import io.xmeta.admin.resolver.EntityVersionResolver;
import io.xmeta.admin.service.EntityFieldService;
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
