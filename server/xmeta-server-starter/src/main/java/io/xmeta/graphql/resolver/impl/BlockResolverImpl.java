package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Block;
import io.xmeta.graphql.model.BlockVersion;
import io.xmeta.graphql.model.BlockVersionOrderByInput;
import io.xmeta.graphql.model.BlockVersionWhereInput;
import io.xmeta.graphql.resolver.BlockResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlockResolverImpl implements BlockResolver {
    @Override
    public List<BlockVersion> versions(Block block, BlockVersionWhereInput where, BlockVersionOrderByInput orderBy, Integer skip, Integer take) throws Exception {
        return null;
    }
}
