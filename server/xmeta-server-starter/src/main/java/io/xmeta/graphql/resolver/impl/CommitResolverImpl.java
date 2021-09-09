package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.CommitResolver;
import io.xmeta.graphql.service.BlockService;
import io.xmeta.graphql.service.BuildService;
import io.xmeta.graphql.service.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommitResolverImpl implements CommitResolver {

    private BuildService buildService;
    private BlockService blockService;
    private EntityService entityService;

    @Override
    public List<Build> builds(Commit commit, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return this.buildService.builds(null, commit, where, orderBy, take, skip);
    }

    public List<PendingChange> changes(Commit commit) {
        List<PendingChange> changes = entityService.getChangedEntitiesByCommit(commit.getId());
        changes.addAll(blockService.getChangedBlocksByCommit(commit.getId()));
        return changes;
    }
}
