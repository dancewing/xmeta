package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Block;
import io.xmeta.graphql.model.BlockVersion;
import io.xmeta.graphql.model.BlockVersionOrderByInput;
import io.xmeta.graphql.model.BlockVersionWhereInput;
import io.xmeta.graphql.resolver.BlockResolver;
import io.xmeta.graphql.service.BlockVersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BlockResolverImpl implements BlockResolver {
    private final BlockVersionService blockVersionService;

    @Override
    public List<BlockVersion> versions(Block block, BlockVersionWhereInput where, BlockVersionOrderByInput orderBy, Integer skip, Integer take) {
        return this.blockVersionService.versions(block, where, orderBy, skip, take);
    }
}
