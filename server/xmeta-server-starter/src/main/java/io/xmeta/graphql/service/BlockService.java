package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.BlockEntity;
import io.xmeta.graphql.repository.BlockRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class BlockService extends BaseService<BlockRepository, BlockEntity, String> {

    private final BlockRepository blockRepository;

    public BlockService(BlockRepository blockRepository) {
        super(blockRepository);
        this.blockRepository = blockRepository;
    }
}
