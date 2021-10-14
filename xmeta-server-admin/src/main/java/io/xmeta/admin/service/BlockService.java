package io.xmeta.admin.service;

import io.xmeta.admin.domain.AppEntity;
import io.xmeta.admin.domain.BlockEntity;
import io.xmeta.admin.domain.BlockVersionEntity;
import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.repository.BlockRepository;
import io.xmeta.admin.repository.BlockVersionRepository;
import io.xmeta.admin.util.Maps;
import io.xmeta.core.utils.ObjectMapperUtils;
import io.xmeta.admin.util.PredicateBuilder;
import io.xmeta.admin.domain.*;
import io.xmeta.admin.mapper.BlockMapper;
import io.xmeta.admin.model.*;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("isAuthenticated()")
public class BlockService extends BaseService<BlockRepository, BlockEntity, String> {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;
    private final BlockVersionRepository blockVersionRepository;
    private final LockService lockService;

    public BlockService(BlockRepository blockRepository, BlockMapper blockMapper, BlockVersionRepository blockVersionRepository, LockService lockService) {
        super(blockRepository);
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
        this.blockVersionRepository = blockVersionRepository;
        this.lockService = lockService;
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

    public List<PendingChange> getChangedBlocksByCommit(String commitId) {
        List<PendingChange> pendingChanges = new ArrayList<>();

        List<BlockEntity> changedBlocks = this.blockRepository.findChangedBlocks(commitId);
        changedBlocks.forEach(blockEntity -> {

            List<BlockVersionEntity> versions = blockEntity.getVersions();
            if (versions.size() == 0) {
                throw new RuntimeException("no block versions");
            }
            Block block = this.blockMapper.toDto(blockEntity);
            BlockVersionEntity changedVersion = versions.get(0);

            EnumPendingChangeAction action = blockEntity.getDeletedAt() != null
                    ? EnumPendingChangeAction.Delete
                    : changedVersion.getVersionNumber() > 1
                    ? EnumPendingChangeAction.Update
                    : EnumPendingChangeAction.Create;

            if (action == EnumPendingChangeAction.Delete) {
                block.setDisplayName(changedVersion.getDisplayName());
            }
            PendingChange pendingChange = new PendingChange();
            pendingChange.setAction(action);
            pendingChange.setResourceType(EnumPendingChangeResourceType.Block);
            pendingChange.setResourceId(block.getId());
            pendingChange.setResource(block);
            pendingChange.setVersionNumber(changedVersion.getVersionNumber());

            pendingChanges.add(pendingChange);
        });

        return pendingChanges;
    }

    @Transactional(readOnly = true)
    public <T extends IBlock> List<T> findByBlockType(String appId, EnumBlockType blockType, Class<T> blockClass) {
        //只查找version number = 0
        List<BlockEntity> byBlockType = this.blockRepository.findByBlockType(appId, blockType.name());
        List<T> blocks = new ArrayList<>();
        byBlockType.forEach(blockEntity -> {
            List<BlockVersionEntity> versions = blockEntity.getVersions();
            if (versions.size() == 0) {
                throw new RuntimeException("can't find block version");
            }
            blocks.add(this.versionToIBlock(blockEntity, versions.get(0), blockClass));
        });
        return blocks;
    }

    public <T extends IBlock> T versionToIBlock(BlockEntity blockEntity, BlockVersionEntity blockVersionEntity,
                                                Class<T> blockClass) {

        byte[] settings = blockVersionEntity.getSettings();
        T block = ObjectMapperUtils.toClass(settings, blockClass);
        if (block == null) {
            throw new RuntimeException("can't convert block");
        }
        if (block instanceof AppSettings) {
            AppSettings appSettings = (AppSettings) block;
            appSettings.setId(blockEntity.getId());
            appSettings.setCreatedAt(blockEntity.getCreatedAt());
            appSettings.setUpdatedAt(blockEntity.getUpdatedAt());
            appSettings.setParentBlock(null);
            appSettings.setDisplayName(blockEntity.getDisplayName());
            appSettings.setDescription(blockEntity.getDescription());
            appSettings.setBlockType(EnumBlockType.AppSettings);
            appSettings.setVersionNumber(0.0D);
            //   appSettings.setInputParameters(blockVersionEntity.getInputParameters());
            //    appSettings.setOutputParameters(Lists.newArrayList());
            appSettings.setLockedByUserId(blockEntity.getLockedByUser() != null ? blockEntity.getLockedByUser().getId() :
                    null);
            appSettings.setLockedAt(blockEntity.getLockedAt());
        }
        return block;
    }

    @Transactional
    public <T extends IBlock> T createBlock(String appId, T settings, EnumBlockType blockType, Block parent) {
        if (parent != null) {
            parent = this.resolveParentBlock(parent.getId(), appId);
        }

//        if (parent!=null && !this.canUseParentType(blockType, parent.getBlockType())) {
//
//        }
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        AppEntity appEntity = new AppEntity();
        appEntity.setId(appId);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(authUser.getUserId());

        BlockEntity blockEntity = new BlockEntity();
        blockEntity.setCreatedAt(ZonedDateTime.now());
        blockEntity.setUpdatedAt(ZonedDateTime.now());
        blockEntity.setApp(appEntity);
        blockEntity.setParentBlock(null);
        blockEntity.setBlockType(blockType.name());
        blockEntity.setDisplayName(settings.getDisplayName());
        blockEntity.setDescription(settings.getDescription());
        blockEntity.setLockedByUser(userEntity);
        blockEntity.setLockedAt(ZonedDateTime.now());

        this.blockRepository.saveAndFlush(blockEntity);

        BlockVersionEntity versionEntity = new BlockVersionEntity();
        versionEntity.setCreatedAt(ZonedDateTime.now());
        versionEntity.setUpdatedAt(ZonedDateTime.now());
        versionEntity.setBlock(blockEntity);
        versionEntity.setVersionNumber(0);
        versionEntity.setInputParameters("");
        versionEntity.setOutputParameters("");
        versionEntity.setSettings(parseSettings(settings));
        versionEntity.setDisplayName(settings.getDisplayName());
        versionEntity.setDescription(settings.getDescription());
        versionEntity.setCommit(null);

        this.blockVersionRepository.saveAndFlush(versionEntity);

        return (T) this.versionToIBlock(blockEntity, versionEntity, settings.getClass());
    }

    private <T extends IBlock> byte[] parseSettings(T settings) {
        Maps.MapBuilder builder = null;
        if (settings instanceof AppSettings) {
            AppSettings appSettings = (AppSettings) settings;
            builder = Maps.of("dbHost", appSettings.getDbHost())
                    .and("dbName", appSettings.getDbName())
                    .and("dbUser", appSettings.getDbUser())
                    .and("dbPassword", appSettings.getDbPassword())
                    .and("dbPort", appSettings.getDbPort())
                    .and("authProvider", appSettings.getAuthProvider());
        }
        return ObjectMapperUtils.toBytes(builder.build());
    }

    public Block resolveParentBlock(String blockId, String appId) {
        List<BlockEntity> blockEntities = this.blockRepository.findByApp(appId, blockId);
        if (blockEntities.size() == 0) {
            throw new RuntimeException("Can't find parent block with ID " + blockId);
        }
        if (blockEntities.size() == 1) {
            return this.blockMapper.toDto(blockEntities.get(0));
        }
        throw new RuntimeException("Unexpected length of matchingBlocks");
    }

    @Transactional
    public <T extends IBlock> T updateBlock(String blockId, T data, Class<T> blockClass) {

        BlockEntity blockEntity = this.lockService.acquireBlockLock(blockId);

        BlockVersionEntity blockCurrentVersion = this.blockVersionRepository.findBlockCurrentVersion(blockId);
        blockCurrentVersion.setSettings(parseSettings(data));
        this.blockVersionRepository.save(blockCurrentVersion);
        return this.versionToIBlock(blockEntity, blockCurrentVersion, blockClass);
    }
}
