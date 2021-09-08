package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.BlockEntity;
import io.xmeta.graphql.domain.BlockVersionEntity;
import io.xmeta.graphql.domain.CommitEntity;
import io.xmeta.graphql.mapper.BlockVersionMapper;
import io.xmeta.graphql.model.BlockVersion;
import io.xmeta.graphql.repository.BlockRepository;
import io.xmeta.graphql.repository.BlockVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
public class BlockVersionService extends BaseService<BlockVersionRepository, BlockVersionEntity, String> {

    private final BlockVersionRepository blockVersionRepository;
    private final BlockVersionMapper blockVersionMapper;
    private final BlockRepository blockRepository;

    public BlockVersionService(BlockVersionRepository blockversionRepository, BlockVersionMapper blockVersionMapper, BlockRepository blockRepository) {
        super(blockversionRepository);
        this.blockVersionRepository = blockversionRepository;
        this.blockVersionMapper = blockVersionMapper;
        this.blockRepository = blockRepository;
    }

    @Transactional
    public BlockVersion createVersion(String blockId, String commitId) {
        BlockEntity blockEntity = this.blockRepository.getById(blockId);
        List<BlockVersionEntity> blockVersions = this.blockVersionRepository.findBlockVersions(blockId);
        if (blockVersions.size() == 0) {
            throw new RuntimeException("Block " + blockId + " has no versions");
        }
        BlockVersionEntity currentVersion = blockVersions.get(0);
        BlockVersionEntity lastVersion = blockVersions.get(blockVersions.size() - 1);
        Integer lastVersionNumber = lastVersion.getVersionNumber();

        Integer nextVersionNumber = lastVersionNumber + 1;

        CommitEntity commit = new CommitEntity();
        commit.setId(commitId);

        BlockVersionEntity blockVersionEntity = new BlockVersionEntity();
        blockVersionEntity.setVersionNumber(nextVersionNumber);
        blockVersionEntity.setInputParameters(currentVersion.getInputParameters());
        blockVersionEntity.setOutputParameters(currentVersion.getOutputParameters());
        blockVersionEntity.setDisplayName(currentVersion.getDisplayName());
        blockVersionEntity.setDescription(currentVersion.getDescription());
        blockVersionEntity.setCommit(commit);
        blockVersionEntity.setBlock(blockEntity);
        // blockVersionEntity.setDeleted(false);

        this.blockVersionRepository.save(blockVersionEntity);

        return this.blockVersionMapper.toDto(blockVersionEntity);

    }
}
