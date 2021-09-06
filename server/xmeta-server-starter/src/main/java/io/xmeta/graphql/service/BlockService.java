package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.BlockEntity;
import io.xmeta.graphql.domain.BlockVersionEntity;
import io.xmeta.graphql.mapper.BlockMapper;
import io.xmeta.graphql.model.Block;
import io.xmeta.graphql.model.EnumPendingChangeAction;
import io.xmeta.graphql.model.EnumPendingChangeResourceType;
import io.xmeta.graphql.model.PendingChange;
import io.xmeta.graphql.repository.BlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional
public class BlockService extends BaseService<BlockRepository, BlockEntity, String> {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public BlockService(BlockRepository blockRepository, BlockMapper blockMapper) {
        super(blockRepository);
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
    }

    public List<PendingChange> getChangedBlocks(String appId, String userId) {
        List<PendingChange> pendingChanges = new ArrayList<>();

        List<BlockEntity> changedBlocks = this.blockRepository.findChangedBlocks(appId, userId);
        changedBlocks.forEach(blockEntity -> {
            List<BlockVersionEntity> versions = blockEntity.getVersions();
            if (versions.size()==0) {
                throw new RuntimeException("no block versions");
            }
            Block block = this.blockMapper.toDto(blockEntity);
            BlockVersionEntity lastVersion = versions.get(0);

            EnumPendingChangeAction action = blockEntity.getDeletedAt() != null
                    ? EnumPendingChangeAction.Delete
                    : versions.size() > 1
                    ? EnumPendingChangeAction.Update
                    : EnumPendingChangeAction.Create;

            PendingChange pendingChange = new PendingChange();
            pendingChange.setAction(action);
            pendingChange.setResourceType(EnumPendingChangeResourceType.Block);
            pendingChange.setResourceId(block.getId());
            pendingChange.setResource(block);
            pendingChange.setVersionNumber(lastVersion.getVersionNumber() + 1);

            pendingChanges.add(pendingChange);
        });

        return pendingChanges;
    }
}
