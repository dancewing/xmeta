package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Build;
import io.xmeta.graphql.model.BuildOrderByInput;
import io.xmeta.graphql.model.BuildWhereInput;
import io.xmeta.graphql.model.Commit;
import io.xmeta.graphql.resolver.CommitResolver;
import io.xmeta.graphql.service.BuildService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommitResolverImpl implements CommitResolver {

    private BuildService buildService;

    @Override
    public List<Build> builds(Commit commit, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return this.buildService.builds(null, commit, where, orderBy, take, skip);
    }
}
