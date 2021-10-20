package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.model.Block;
import io.xmeta.admin.model.BlockVersion;
import io.xmeta.admin.model.BlockVersionOrderByInput;
import io.xmeta.admin.model.BlockVersionWhereInput;
import io.xmeta.admin.resolver.BlockResolver;
import io.xmeta.admin.service.BlockVersionService;
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
