package io.xmeta.admin.resolver;

import io.xmeta.admin.model.Build;
import io.xmeta.admin.model.BuildOrderByInput;
import io.xmeta.admin.model.BuildWhereInput;
import io.xmeta.admin.model.Commit;

/**
 * Resolver for Commit
 */
public interface CommitResolver extends graphql.kickstart.tools.GraphQLResolver<Commit> {

    @javax.validation.constraints.NotNull
    java.util.List<Build> builds(Commit commit, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip);

}
