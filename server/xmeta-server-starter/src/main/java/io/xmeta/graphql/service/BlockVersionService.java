package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.BlockVersionMapper;
import io.xmeta.graphql.model.Block;
import io.xmeta.graphql.model.BlockVersion;
import io.xmeta.graphql.model.BlockVersionOrderByInput;
import io.xmeta.graphql.model.BlockVersionWhereInput;
import io.xmeta.graphql.repository.BlockRepository;
import io.xmeta.graphql.repository.BlockVersionRepository;
import io.xmeta.graphql.util.PredicateBuilder;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
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
public class BlockVersionService extends BaseService<BlockVersionRepository, BlockVersionEntity, String> {

    private final BlockVersionRepository blockVersionRepository;
    private final BlockVersionMapper blockVersionMapper;
    private final BlockRepository blockRepository;
    private final LockService lockService;

    public BlockVersionService(BlockVersionRepository blockversionRepository, BlockVersionMapper blockVersionMapper, BlockRepository blockRepository, LockService lockService) {
        super(blockversionRepository);
        this.blockVersionRepository = blockversionRepository;
        this.blockVersionMapper = blockVersionMapper;
        this.blockRepository = blockRepository;
        this.lockService = lockService;
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
        blockVersionEntity.setCreatedAt(ZonedDateTime.now());
        blockVersionEntity.setUpdatedAt(ZonedDateTime.now());
        blockVersionEntity.setVersionNumber(nextVersionNumber);
        blockVersionEntity.setInputParameters(currentVersion.getInputParameters());
        blockVersionEntity.setOutputParameters(currentVersion.getOutputParameters());
        blockVersionEntity.setDisplayName(currentVersion.getDisplayName());
        blockVersionEntity.setDescription(currentVersion.getDescription());
        blockVersionEntity.setCommit(commit);
        blockVersionEntity.setBlock(blockEntity);
        blockVersionEntity.setSettings(currentVersion.getSettings());
        // blockVersionEntity.setDeleted(false);

        this.blockVersionRepository.save(blockVersionEntity);

        return this.blockVersionMapper.toDto(blockVersionEntity);

    }

    @Transactional
    public void discardPendingChanges(String blockId, String userId) {
        List<BlockVersionEntity> blockVersions = this.blockVersionRepository.findBlockVersions(blockId);
        if (blockVersions.size() == 0) {
            throw new RuntimeException("Block " + blockId + " has no versions");
        }
        BlockVersionEntity firstEntityVersion = blockVersions.get(0);
        BlockVersionEntity lastEntityVersion = blockVersions.get(blockVersions.size() - 1);
        if (firstEntityVersion.getBlock().getLockedByUser() != null &&
                !StringUtils.equals(firstEntityVersion.getBlock().getLockedByUser().getId(), userId)) {
            throw new RuntimeException("Cannot discard pending changes on Block " + blockId + " since it is not " +
                    "currently " +
                    "locked" +
                    " by the requesting user ");
        }

        this.cloneVersionData(lastEntityVersion.getId(), firstEntityVersion.getId());

        this.lockService.releaseBlockLock(blockId);
    }

    private void cloneVersionData(String sourceVersionId, String targetVersionId) {
        BlockVersionEntity sourceVersion = this.blockVersionRepository.getById(sourceVersionId);
        BlockVersionEntity targetVersion = this.blockVersionRepository.getById(targetVersionId);

        if (sourceVersion.getDeleted() == null) {
            BlockEntity block = targetVersion.getBlock();
            block.setDisplayName(sourceVersion.getDisplayName());
            block.setDescription(sourceVersion.getDescription());
            block.setDeletedAt(null);
            this.blockRepository.save(block);
        }
        targetVersion.setDisplayName(sourceVersion.getDisplayName());
        targetVersion.setDescription(sourceVersion.getDescription());
        targetVersion.setSettings(sourceVersion.getSettings());
        targetVersion.setInputParameters(sourceVersion.getInputParameters());
        targetVersion.setOutputParameters(sourceVersion.getOutputParameters());
        this.blockVersionRepository.save(targetVersion);
    }

    public List<BlockVersion> versions(Block block, BlockVersionWhereInput where, BlockVersionOrderByInput orderBy, Integer skip, Integer take) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        Specification<BlockVersionEntity> specification = Specification.where(null);
        Specification<BlockVersionEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (block != null) {
                Join<Object, Object> join = root.join(BlockVersionEntity_.BLOCK, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(BlockEntity_.ID),
                        block.getId()));
            }
            if (where != null) {
                if (where.getId() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(BlockVersionEntity_.ID),
                            where.getId()));
                }
                if (where.getVersionNumber() != null) {
                    predicates.addAll(PredicateBuilder.buildIntFilter(criteriaBuilder,
                            root.get(BlockVersionEntity_.VERSION_NUMBER),
                            where.getVersionNumber()));
                }
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<BlockVersionEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.blockVersionRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.blockVersionRepository.findAll(specification, sort);
        }
        return result.stream().map(this.blockVersionMapper::toDto).collect(Collectors.toList());
    }
}
