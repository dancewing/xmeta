package io.xmeta.admin.service;

import io.xmeta.admin.constants.EntityConst;
import io.xmeta.admin.domain.AppEntity;
import io.xmeta.admin.domain.EntityEntity;
import io.xmeta.admin.domain.EntityVersionEntity;
import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.mix.CreateOneEntityField;
import io.xmeta.admin.mix.EntityDomain;
import io.xmeta.admin.repository.EntityRepository;
import io.xmeta.admin.repository.EntityVersionRepository;
import io.xmeta.admin.util.Maps;
import io.xmeta.admin.util.PredicateBuilder;
import io.xmeta.admin.util.SoftDelete;
import io.xmeta.admin.domain.*;
import io.xmeta.admin.mapper.EntityMapper;
import io.xmeta.admin.model.*;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
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
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@Slf4j
@PreAuthorize("isAuthenticated()")
public class EntityService extends BaseService<EntityRepository, EntityEntity, String> {

    private final EntityRepository entityRepository;
    private final EntityVersionRepository entityVersionRepository;
    private final EntityMapper entityMapper;
    private final EntityPermissionService entityPermissionService;
    private final EntityFieldService entityFieldService;
    private final Validator validator;
    private final LockService lockService;

    public EntityService(EntityRepository entityRepository, EntityVersionRepository entityVersionRepository, EntityMapper entityMapper, EntityPermissionService entityPermissionService, EntityFieldService entityFieldService, Validator validator, LockService lockService) {
        super(entityRepository);
        this.entityRepository = entityRepository;
        this.entityVersionRepository = entityVersionRepository;
        this.entityMapper = entityMapper;
        this.entityPermissionService = entityPermissionService;
        this.entityFieldService = entityFieldService;
        this.validator = validator;
        this.lockService = lockService;
    }

    @Transactional
    public void createDefaultEntities(String appId, String userId) {

        AppEntity appEntity = new AppEntity();
        appEntity.setId(appId);

        UserEntity user = new UserEntity();
        user.setId(userId);

        WhereParentIdInput app = WhereParentIdInput.builder().setConnect(WhereUniqueInput.builder().setId(appId).build()).build();
        List<EntityConst.EntityTemplate> defaultEntities = EntityConst.getDefaultEntities();
        for (EntityConst.EntityTemplate entityTemplate : defaultEntities) {
            entityTemplate.setApp(app);
            Entity entity = this.createOneEntity(entityTemplate);
            if (entityTemplate.getFields() != null) {
                for (EntityFieldCreateInput field : entityTemplate.getFields()) {
                    CreateOneEntityField createOneEntityField = new CreateOneEntityField();
                    field.setEntity(WhereParentIdInput.builder()
                            .setConnect(WhereUniqueInput.builder().setId(entity.getId()).build())
                            .build());
                    if (StringUtils.isEmpty(field.getColumn())) {
                        field.setColumn(field.getName());
                    }
                    createOneEntityField.setData(field);
                    this.entityFieldService.createField(createOneEntityField);
                }
            }
        }
    }

    public List<Entity> entities(App app, EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        Specification<EntityEntity> specification = Specification.where(null);
        Specification<EntityEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (app != null && app.getId() != null) {
                Join<Object, Object> join = root.join(EntityEntity_.APP, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), app.getId()));
            }
            if (where != null) {
                if (where.getApp() != null && where.getApp().getId() != null) {
                    Join<Object, Object> join = root.join(EntityEntity_.APP, JoinType.LEFT);
                    predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), where.getApp().getId()));
                }
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<EntityEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.entityRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.entityRepository.findAll(specification, sort);
        }
        return result.stream().map(this.entityMapper::toDto).collect(Collectors.toList());
    }

    public List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return this.entities(null, where, orderBy, skip, take);
    }

    @Transactional
    public Entity createOneEntity(EntityCreateInput data) {
        Set<ConstraintViolation<EntityCreateInput>> constraintViolations = validator.validate(data);
        if (constraintViolations.size() > 0) {
            throw new RuntimeException("");
        }

        //The entity name and plural display name cannot be the same.
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        UserEntity user = new UserEntity();
        user.setId(authUser.getUserId());

        EntityEntity entityEntity = new EntityEntity();
        entityEntity.setCreatedAt(ZonedDateTime.now());
        entityEntity.setUpdatedAt(ZonedDateTime.now());

        AppEntity appEntity = new AppEntity();
        appEntity.setId(data.getApp().getConnect().getId());
        entityEntity.setApp(appEntity);
        entityEntity.setName(data.getName());
        entityEntity.setTable(data.getTable());
        entityEntity.setDisplayName(data.getDisplayName());
        entityEntity.setPluralDisplayName(data.getPluralDisplayName());
        entityEntity.setDescription("");
        entityEntity.setLockedByUser(user);
        entityEntity.setLockedAt(ZonedDateTime.now());
        //   entityEntity.setDeletedAt(ZonedDateTime.now());
        this.entityRepository.saveAndFlush(entityEntity);

        //create Version
        EntityVersionEntity entityVersionEntity = new EntityVersionEntity();
        entityVersionEntity.setCreatedAt(ZonedDateTime.now());
        entityVersionEntity.setUpdatedAt(ZonedDateTime.now());
        entityVersionEntity.setEntity(entityEntity);
        entityVersionEntity.setVersionNumber(0);
        entityVersionEntity.setName(data.getName());
        entityVersionEntity.setDisplayName(data.getDisplayName());
        entityVersionEntity.setPluralDisplayName(data.getPluralDisplayName());
        entityVersionEntity.setDescription("");
        entityVersionEntity.setCommit(null);
        entityVersionEntity.setDeleted(null);
        //entityVersionEntity.setBuilds(Sets.newHashSet());
        this.entityVersionRepository.saveAndFlush(entityVersionEntity);

        //create default permissions;
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Create.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Update.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.View.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Delete.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Search.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);

        //create default entity fields

        //@format off
        this.entityFieldService.createDefaultField(EntityField.builder()
                        .setDataType(EnumDataType.Id)
                        .setName("id")
                        .setDisplayName("ID")
                        .setDescription("An automatically created unique identifier of the entity")
                        .setUnique(false)
                        .setRequired(true)
                        .setSearchable(true)
                        .setColumn("id")
                        .setProperties(Maps.empty())
                        .build(), entityEntity.getId(),
                entityVersionEntity);

        this.entityFieldService.createDefaultField(EntityField.builder()
                        .setDataType(EnumDataType.CreatedAt)
                        .setName("createdAt")
                        .setDisplayName("Created At")
                        .setDescription("An automatically created field of the time the entity created at")
                        .setUnique(false)
                        .setRequired(true)
                        .setSearchable(false)
                        .setColumn("created_at")
                        .setProperties(Maps.empty())
                        .build(), entityEntity.getId(),
                entityVersionEntity);

        this.entityFieldService.createDefaultField(EntityField.builder()
                        .setDataType(EnumDataType.UpdatedAt)
                        .setName("updatedAt")
                        .setDisplayName("Updated At")
                        .setDescription("An automatically created field of the last time the entity updated at")
                        .setUnique(false)
                        .setRequired(true)
                        .setSearchable(false)
                        .setColumn("updated_at")
                        .setProperties(Maps.empty())
                        .build(), entityEntity.getId(),
                entityVersionEntity);

        return this.entityMapper.toDto(entityEntity);
    }

    @Transactional(readOnly = true)
    public List<PendingChange> getChangedEntities(String appId, String userId) {
        List<PendingChange> pendingChanges = new ArrayList<>();
        List<EntityEntity> changedEntities = this.entityRepository.findChangedEntities(appId, userId);
        changedEntities.forEach(entityEntity -> {
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("versionNumber")));
            Page<EntityVersionEntity> page = this.entityVersionRepository.findEntityVersions(entityEntity.getId(),
                    pageable);

            // List<EntityVersionEntity> versions = entityEntity.getVersions();

            if (page.getContent().size() == 0) {
                log.error("no entity versions {}, {}", entityEntity.getId(), entityEntity.getName());
                throw new RuntimeException("no entity versions");
            }
            Entity entity = this.entityMapper.toDto(entityEntity);

            EntityVersionEntity lastVersion = page.getContent().get(0);

            EnumPendingChangeAction action = entityEntity.getDeletedAt() != null
                    ? EnumPendingChangeAction.Delete
                    : page.getTotalElements() > 1
                    ? EnumPendingChangeAction.Update
                    : EnumPendingChangeAction.Create;

            if (action == EnumPendingChangeAction.Delete) {
                entity.setName(SoftDelete.revertDeletedItemName(entity.getName(), entity.getId()));
                entity.setDisplayName(SoftDelete.revertDeletedItemName(entity.getDisplayName(), entity.getId()));
                entity.setPluralDisplayName(SoftDelete.revertDeletedItemName(entity.getPluralDisplayName(), entity.getId()));
            }

            PendingChange pendingChange = new PendingChange();
            pendingChange.setAction(action);
            pendingChange.setResourceType(EnumPendingChangeResourceType.Entity);
            pendingChange.setResourceId(entity.getId());
            pendingChange.setResource(entity);
            pendingChange.setVersionNumber(lastVersion.getVersionNumber() + 1);

            pendingChanges.add(pendingChange);
        });

        return pendingChanges;
    }

    public List<PendingChange> getChangedEntitiesByCommit(String commitId) {
        List<PendingChange> pendingChanges = new ArrayList<>();
        List<EntityEntity> changedEntities = this.entityRepository.findChangedEntities(commitId);
        changedEntities.forEach(entityEntity -> {
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("versionNumber")));
            Page<EntityVersionEntity> page = this.entityVersionRepository.findEntityVersions(entityEntity.getId(),
                    pageable);

            // List<EntityVersionEntity> versions = entityEntity.getVersions();

            if (page.getContent().size() == 0) {
                log.error("no entity versions {}, {}", entityEntity.getId(), entityEntity.getName());
                throw new RuntimeException("no entity versions");
            }

            Entity entity = this.entityMapper.toDto(entityEntity);

            EntityVersionEntity changedVersion = page.getContent().get(0);

            EnumPendingChangeAction action = entityEntity.getDeletedAt() != null
                    ? EnumPendingChangeAction.Delete
                    : changedVersion.getVersionNumber() > 1
                    ? EnumPendingChangeAction.Update
                    : EnumPendingChangeAction.Create;

            if (action == EnumPendingChangeAction.Delete) {
                entity.setName(changedVersion.getName());
                entity.setDisplayName(changedVersion.getDisplayName());
                entity.setPluralDisplayName(changedVersion.getPluralDisplayName());
            }

            PendingChange pendingChange = new PendingChange();
            pendingChange.setAction(action);
            pendingChange.setResourceType(EnumPendingChangeResourceType.Entity);
            pendingChange.setResourceId(entity.getId());
            pendingChange.setResource(entity);
            pendingChange.setVersionNumber(changedVersion.getVersionNumber());

            pendingChanges.add(pendingChange);
        });

        return pendingChanges;
    }

    public Entity getEntity(String entityId) {
        return this.entityMapper.toDto(this.entityRepository.getById(entityId));
    }

    @Transactional
    public Entity updateEntity(EntityUpdateInput data, WhereUniqueInput where) {
        EntityEntity entityEntity = this.lockService.acquireEntityLock(where.getId());
        entityEntity.setName(data.getName());
        entityEntity.setDisplayName(data.getDisplayName());
        entityEntity.setPluralDisplayName(data.getPluralDisplayName());
        entityEntity.setDescription(data.getDescription());
        entityEntity.setTable(data.getName());

        this.entityRepository.save(entityEntity);

        EntityVersionEntity versionEntity = this.entityVersionRepository.findEntityVersion(where.getId(), 0);
        if (versionEntity == null) {
            throw new RuntimeException();
        } else {
            versionEntity.setName(data.getName());
            versionEntity.setDisplayName(data.getDisplayName());
            versionEntity.setPluralDisplayName(data.getPluralDisplayName());
            versionEntity.setDescription(data.getDescription());
            this.entityVersionRepository.save(versionEntity);
        }
        return this.entityMapper.toDto(entityEntity);
    }

    @Transactional
    public Entity deleteEntity(WhereUniqueInput where) {
        EntityEntity entityEntity = this.lockService.acquireEntityLock(where.getId());

        entityEntity.setName(SoftDelete.prepareDeletedItemName(entityEntity.getName(), entityEntity.getId()));
        entityEntity.setDisplayName(SoftDelete.prepareDeletedItemName(entityEntity.getDisplayName(), entityEntity.getId()));
        entityEntity.setPluralDisplayName(SoftDelete.prepareDeletedItemName(entityEntity.getPluralDisplayName(), entityEntity.getId()));

        this.entityRepository.save(entityEntity);

        EntityVersionEntity versionEntity = this.entityVersionRepository.findEntityVersion(where.getId(), 0);
        if (versionEntity == null) {
            throw new RuntimeException();
        } else {
            versionEntity.setDeleted(true);
            this.entityVersionRepository.save(versionEntity);
        }
        return this.entityMapper.toDto(entityEntity);
    }

    public Entity lockEntity(WhereUniqueInput where) {
        return this.entityMapper.toDto(this.lockService.acquireEntityLock(where.getId()));
    }

    public List<EntityDomain> loadEntities(String appId) {
        List<EntityEntity> entityEntities = this.entityRepository.findEntitiesByApp(appId);
        List<EntityDomain> entityDomains = new ArrayList<>();
        for (EntityEntity entity: entityEntities) {
            EntityDomain entityDomain = new EntityDomain();
            entityDomain.setId(entity.getId());
            entityDomain.setName(entity.getName());
            entityDomain.setDisplayName(entity.getDisplayName());
            entityDomain.setPluralDisplayName(entity.getPluralDisplayName());
            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("versionNumber")));
            Page<EntityVersionEntity> page = this.entityVersionRepository.findEntityVersions(entity.getId(),
                    pageable);
            if (page.getContent().size() > 0) {
                EntityVersionEntity entityVersionEntity = page.getContent().get(0);
                entityDomain.setVersionNumber(entityVersionEntity.getVersionNumber());
                entityDomain.setFields(this.entityFieldService.getFields(entityVersionEntity.getId()));
            }
            entityDomains.add(entityDomain);
        }
        return entityDomains;
    }
}
