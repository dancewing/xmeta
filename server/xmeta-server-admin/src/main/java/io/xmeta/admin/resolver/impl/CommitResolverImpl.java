package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.service.BlockService;
import io.xmeta.admin.service.BuildService;
import io.xmeta.admin.service.EntityService;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.resolver.CommitResolver;
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
