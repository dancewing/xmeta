package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Build;
import io.xmeta.graphql.model.BuildOrderByInput;
import io.xmeta.graphql.model.BuildWhereInput;
import io.xmeta.graphql.model.Commit;
import io.xmeta.graphql.resolver.CommitResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommitResolverImpl implements CommitResolver {
    @Override
    public List<Build> builds(Commit commit, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return null;
    }
}
