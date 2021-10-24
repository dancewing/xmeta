package io.xmeta.admin.resolver;

import io.xmeta.admin.model.EntityField;
import io.xmeta.admin.model.EntityFieldOrderByInput;
import io.xmeta.admin.model.EntityFieldWhereInput;
import io.xmeta.admin.model.EntityVersion;

/**
 * Resolver for EntityVersion
 */
public interface EntityVersionResolver extends graphql.kickstart.tools.GraphQLResolver<EntityVersion> {

    @javax.validation.constraints.NotNull
    java.util.List<EntityField> fields(EntityVersion entityVersion, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take);

}
