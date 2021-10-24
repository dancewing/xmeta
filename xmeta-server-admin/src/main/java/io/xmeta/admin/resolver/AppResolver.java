package io.xmeta.admin.resolver;

import io.xmeta.admin.model.*;

/**
 * Resolver for App
 */
public interface AppResolver extends graphql.kickstart.tools.GraphQLResolver<App> {

    @javax.validation.constraints.NotNull
    java.util.List<Entity> entities(App app, EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take);

    @javax.validation.constraints.NotNull
    java.util.List<Build> builds(App app, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip);

}
