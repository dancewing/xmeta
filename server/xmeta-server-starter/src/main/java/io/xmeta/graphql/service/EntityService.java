package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.EntityMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.EntityFieldRepository;
import io.xmeta.graphql.repository.EntityRepository;
import io.xmeta.graphql.repository.EntityVersionRepository;
import io.xmeta.graphql.util.PredicateBuilder;
import io.xmeta.graphql.util.SoftDelete;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.HibernateUtils;
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
@Slf4j
public class EntityService extends BaseService<EntityRepository, EntityEntity, String> {

    private final EntityRepository entityRepository;
    private final EntityFieldRepository entityFieldRepository;
    private final EntityVersionRepository entityVersionRepository;
    private final EntityMapper entityMapper;
    private final EntityPermissionService entityPermissionService;
    private final EntityFieldService entityFieldService;

    public EntityService(EntityRepository entityRepository, EntityFieldRepository entityFieldRepository, EntityVersionRepository entityVersionRepository, EntityMapper entityMapper, EntityPermissionService entityPermissionService, EntityFieldService entityFieldService) {
        super(entityRepository);
        this.entityRepository = entityRepository;
        this.entityFieldRepository = entityFieldRepository;
        this.entityVersionRepository = entityVersionRepository;
        this.entityMapper = entityMapper;
        this.entityPermissionService = entityPermissionService;
        this.entityFieldService = entityFieldService;
    }

    @Transactional
    public void createDefaultEntities(String appId, String userId) {
        List<AppCreateWithEntitiesEntityInput> entities = new ArrayList<>();
        AppCreateWithEntitiesEntityInput entitiesEntityInput = new AppCreateWithEntitiesEntityInput();
//        entitiesEntityInput.setName("");
//        entitiesEntityInput.setFields(Lists.newArrayList());
//        entitiesEntityInput.setRelationsToEntityIndex(Lists.newArrayList());

    }

    public List<Entity> entities(App app, EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        Specification<EntityEntity> specification = Specification.where(null);
        Specification<EntityEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (app!=null && app.getId()!=null){
                Join<Object, Object> join = root.join(EntityEntity_.APP, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), app.getId()));
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<EntityEntity> result = null;
        if (skip != null && take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.entityRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.entityRepository.findAll(sort);
        }
        return result.stream().map(this.entityMapper::toDto).collect(Collectors.toList());
    }

    public List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return this.entities(null, where, orderBy, skip, take);
    }

    @Transactional
    public Entity createOneEntity(EntityCreateInput data) {
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
        entityEntity.setDisplayName(data.getDisplayName());
        entityEntity.setPluralDisplayName(data.getPluralDisplayName());
        entityEntity.setDescription(null);
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
        entityVersionEntity.setDescription(null);
        entityVersionEntity.setCommit(null);
        entityVersionEntity.setDeleted(null);
        //entityVersionEntity.setBuilds(Sets.newHashSet());
        this.entityVersionRepository.saveAndFlush(entityVersionEntity);

        log.info("entity versions : {}", entityEntity.getVersions().size());
        log.info("entity versions : {}", this.entityRepository.getById(entityEntity.getId()).getVersions().size());
        log.info("entity versions : {}", this.entityVersionRepository.findEntityDescVersions(entityEntity.getId()).size());


        //create default permissions;
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Create.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Update.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.View.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Delete.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);
        this.entityPermissionService.createEntityPermission(EnumEntityAction.Search.name(), EnumEntityPermissionType.AllRoles.name(), entityVersionEntity);

        //create default entity fields

        //@format off
        this.entityFieldService.create(EntityField.builder()
                        .setDataType(EnumDataType.Id)
                        .setName("id")
                        .setDisplayName("ID")
                        .setDescription("An automatically created unique identifier of the entity")
                        .setUnique(false)
                        .setRequired(true)
                        .setSearchable(true)
                        .setProperties("")
                        .build(), entityEntity.getId(),
                entityVersionEntity);

        this.entityFieldService.create(EntityField.builder()
                        .setDataType(EnumDataType.CreatedAt)
                        .setName("createdAt")
                        .setDisplayName("Created At")
                        .setDescription("An automatically created field of the time the entity created at")
                        .setUnique(false)
                        .setRequired(true)
                        .setSearchable(false)
                        .setProperties("")
                        .build(), entityEntity.getId(),
                entityVersionEntity);

        this.entityFieldService.create(EntityField.builder()
                        .setDataType(EnumDataType.UpdatedAt)
                        .setName("updatedAt")
                        .setDisplayName("Updated At")
                        .setDescription("An automatically created field of the last time the entity updated at")
                        .setUnique(false)
                        .setRequired(true)
                        .setSearchable(false)
                        .setProperties("")
                        .build(), entityEntity.getId(),
                entityVersionEntity);

        return this.entityMapper.toDto(entityEntity);
    }

    @Transactional(readOnly = true)
    public List<PendingChange> getChangedEntities(String appId, String userId) {
        List<PendingChange> pendingChanges = new ArrayList<>();
        List<EntityEntity> changedEntities = this.entityRepository.findChangedEntities(appId, userId);
        changedEntities.forEach(entityEntity -> {

            List<EntityVersionEntity> versions = entityEntity.getVersions();

            if (versions.size() == 0) {
                log.error("no entity versions {}, {}", entityEntity.getId(), entityEntity.getName());
                throw new RuntimeException("no entity versions");
            }
            Entity entity = this.entityMapper.toDto(entityEntity);

            EntityVersionEntity lastVersion = versions.get(0);

            EnumPendingChangeAction action = entityEntity.getDeletedAt() != null
                    ? EnumPendingChangeAction.Delete
                    : versions.size() > 1
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

    public Entity getEntity(String entityId) {
        return this.entityMapper.toDto(this.entityRepository.getById(entityId));
    }
}
