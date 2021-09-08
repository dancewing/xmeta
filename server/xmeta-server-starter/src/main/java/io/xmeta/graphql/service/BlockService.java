package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AppEntity_;
import io.xmeta.graphql.domain.BlockEntity;
import io.xmeta.graphql.domain.BlockEntity_;
import io.xmeta.graphql.domain.BlockVersionEntity;
import io.xmeta.graphql.mapper.BlockMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.BlockRepository;
import io.xmeta.graphql.util.PredicateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class BlockService extends BaseService<BlockRepository, BlockEntity, String> {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public BlockService(BlockRepository blockRepository, BlockMapper blockMapper) {
        super(blockRepository);
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
    }

    public Block getBlock(String id) {
        return this.blockMapper.toDto(this.blockRepository.getById(id));
    }

    public List<Block> blocks(BlockWhereInput where, BlockOrderByInput orderBy, Integer skip, Integer take) {
        Specification<BlockEntity> specification = Specification.where(null);
        Specification<BlockEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (where != null) {
                if (where.getApp() != null && where.getApp().getId() != null) {
                    Join<Object, Object> join = root.join(BlockEntity_.APP, JoinType.LEFT);
                    predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), where.getApp().getId()));
                }
                if (where.getId() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder, root.get(BlockEntity_.ID),
                            where.getId()));
                }
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<BlockEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.blockRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.blockRepository.findAll(specification, sort);
        }
        return result.stream().map(this.blockMapper::toDto).collect(Collectors.toList());
    }

    public List<PendingChange> getChangedBlocks(String appId, String userId) {
        List<PendingChange> pendingChanges = new ArrayList<>();

        List<BlockEntity> changedBlocks = this.blockRepository.findChangedBlocks(appId, userId);
        changedBlocks.forEach(blockEntity -> {
            List<BlockVersionEntity> versions = blockEntity.getVersions();
            if (versions.size() == 0) {
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

    @Transactional
    public void releaseLock(String blockId) {
        this.blockRepository.releaseLock(blockId);
    }
}
