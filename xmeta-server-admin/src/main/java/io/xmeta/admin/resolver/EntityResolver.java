package io.xmeta.admin.resolver;

import io.xmeta.admin.model.*;

/**
 * Resolver for Entity
 */
public interface EntityResolver extends graphql.kickstart.tools.GraphQLResolver<Entity> {

    @javax.validation.constraints.NotNull
    java.util.List<EntityVersion> versions(Entity entity, EntityVersionWhereInput where, EntityVersionOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    java.util.List<EntityField> fields(Entity entity, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take);

}
