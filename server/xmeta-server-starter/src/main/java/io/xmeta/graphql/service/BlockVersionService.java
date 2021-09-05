package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.BlockVersionEntity;
import io.xmeta.graphql.repository.BlockVersionRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class BlockVersionService extends BaseService<BlockVersionRepository, BlockVersionEntity, String> {

    private final BlockVersionRepository blockversionRepository;

    public BlockVersionService(BlockVersionRepository blockversionRepository) {
        super(blockversionRepository);
        this.blockversionRepository = blockversionRepository;
    }
}
