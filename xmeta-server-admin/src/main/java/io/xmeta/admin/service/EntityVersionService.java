package io.xmeta.admin.service;

import io.xmeta.admin.domain.*;
import io.xmeta.admin.mapper.EntityVersionMapper;
import io.xmeta.admin.model.Entity;
import io.xmeta.admin.model.EntityVersion;
import io.xmeta.admin.model.EntityVersionOrderByInput;
import io.xmeta.admin.model.EntityVersionWhereInput;
import io.xmeta.admin.repository.*;
import io.xmeta.admin.util.PredicateBuilder;
import io.xmeta.core.exception.MetaException;
import org.apache.commons.lang.StringUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@PreAuthorize("isAuthenticated()")
public class EntityVersionService extends BaseService<EntityVersionRepository, EntityVersionEntity, String> {

    private final EntityVersionRepository entityVersionRepository;
    private final EntityVersionMapper entityVersionMapper;
    private final EntityRepository entityRepository;
    private final EntityPermissionFieldRepository entityPermissionFieldRepository;
    private final EntityPermissionRoleRepository entityPermissionRoleRepository;
    private final EntityFieldRepository entityFieldRepository;
    private final EntityPermissionRepository entityPermissionRepository;
    private final LockService lockService;

    public EntityVersionService(EntityVersionRepository entityVersionRepository, EntityVersionMapper entityVersionMapper, EntityRepository entityRepository, EntityPermissionFieldRepository entityPermissionFieldRepository, EntityPermissionRoleRepository entityPermissionRoleRepository, EntityFieldRepository entityFieldRepository, EntityPermissionRepository entityPermissionRepository, LockService lockService) {
        super(entityVersionRepository);
        this.entityVersionRepository = entityVersionRepository;
        this.entityVersionMapper = entityVersionMapper;
        this.entityRepository = entityRepository;
        this.entityPermissionFieldRepository = entityPermissionFieldRepository;
        this.entityPermissionRoleRepository = entityPermissionRoleRepository;
        this.entityFieldRepository = entityFieldRepository;
        this.entityPermissionRepository = entityPermissionRepository;
        this.lockService = lockService;
    }

    public List<EntityVersion> versions(Entity entity, EntityVersionWhereInput where, EntityVersionOrderByInput orderBy, Integer skip, Integer take) {
        Specification<EntityVersionEntity> specification = Specification.where(null);
        Specification<EntityVersionEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (entity != null && entity.getId() != null) {
                Join<Object, Object> join = root.join(EntityVersionEntity_.ENTITY, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(EntityEntity_.ID), entity.getId()));
            }
            if (where != null) {
                if (where.getId() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(EntityVersionEntity_.ID),
                            where.getId()));
                }
                if (where.getVersionNumber() != null) {
                    predicates.addAll(PredicateBuilder.buildIntFilter(criteriaBuilder,
                            root.get(EntityVersionEntity_.VERSION_NUMBER),
                            where.getVersionNumber()));
                }
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<EntityVersionEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.entityVersionRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.entityVersionRepository.findAll(sort);
        }
        return result.stream().map(this.entityVersionMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void discardPendingChanges(String entityId, String userId) {
        List<EntityVersionEntity> entityVersions = this.entityVersionRepository.findEntityVersions(entityId);
        if (entityVersions.size() == 0) {
            throw new RuntimeException("Entity " + entityId + " has no versions");
        }
        EntityVersionEntity firstEntityVersion = entityVersions.get(0);
        EntityVersionEntity lastEntityVersion = entityVersions.get(entityVersions.size() - 1);
        if (firstEntityVersion.getEntity().getLockedByUser() != null &&
                !StringUtils.equals(firstEntityVersion.getEntity().getLockedByUser().getId(), userId)) {
            throw new RuntimeException("Cannot discard pending changes on Entity " + entityId + " since it is not currently " +
                    "locked" +
                    " by the requesting user ");
        }

        this.cloneVersionData(lastEntityVersion.getId(), firstEntityVersion.getId());

        this.lockService.releaseEntityLock(entityId);
    }

    @Transactional
    public EntityVersion createVersion(String entityId, String commitId) {
        EntityEntity entityEntity = this.entityRepository.getById(entityId);
        List<EntityVersionEntity> entityVersions = this.entityVersionRepository.findEntityVersions(entityId);
        if (entityVersions.size() == 0) {
            throw new RuntimeException("Entity " + entityId + " has no versions");
        }
        EntityVersionEntity firstEntityVersion = entityVersions.get(0);
        EntityVersionEntity lastEntityVersion = entityVersions.get(entityVersions.size() - 1);
        Integer lastVersionNumber = lastEntityVersion.getVersionNumber();

        Integer nextVersionNumber = lastVersionNumber + 1;
        EntityVersionEntity newEntityVersion = new EntityVersionEntity();
        newEntityVersion.setCreatedAt(ZonedDateTime.now());
        newEntityVersion.setUpdatedAt(ZonedDateTime.now());
        newEntityVersion.setEntity(entityEntity);
        newEntityVersion.setVersionNumber(nextVersionNumber);
        newEntityVersion.setName(firstEntityVersion.getName());
        newEntityVersion.setDisplayName(firstEntityVersion.getDisplayName());
        newEntityVersion.setDescription(firstEntityVersion.getDescription());
        newEntityVersion.setTable(firstEntityVersion.getTable());

        CommitEntity commit = new CommitEntity();
        commit.setId(commitId);
        newEntityVersion.setCommit(commit);

        this.entityVersionRepository.save(newEntityVersion);

        return this.cloneVersionData(firstEntityVersion.getId(), newEntityVersion.getId());

    }

    @Transactional
    public EntityVersion cloneVersionData(String sourceVersionId, String targetVersionId) {

        EntityVersionEntity sourceVersion = this.entityVersionRepository.getById(sourceVersionId);
        EntityVersionEntity targetVersion = this.entityVersionRepository.getById(targetVersionId);

        // Clear any existing fields and permissions when discarding changes and rolling back to previous version
        if (targetVersion.getVersionNumber() == 0) {
            //We use separate actions since prisma does not yet support CASCADE DELETE
            //First delete entityPermissionField and entityPermissionRole
            this.entityPermissionFieldRepository.deleteByEntityVersionId(targetVersionId);
            this.entityPermissionRoleRepository.deleteByEntityVersionId(targetVersionId);

            this.entityFieldRepository.deleteByEntityVersionId(targetVersionId);
            this.entityPermissionRepository.deleteByEntityVersionId(targetVersionId);

        }

        //update the target version with its fields, and the its parent entity
        targetVersion.setName(sourceVersion.getName());
        targetVersion.setDisplayName(sourceVersion.getDisplayName());
        targetVersion.setDescription(sourceVersion.getDescription());
        this.entityVersionRepository.save(targetVersion);

        //create new entity fields from source;
        //List<EntityFieldEntity> fields = sourceVersion.getFields();
        List<EntityFieldEntity> fields = this.entityFieldRepository.getFields(sourceVersion.getId());

        for (EntityFieldEntity entityFieldEntity : fields) {
            EntityFieldEntity newField = new EntityFieldEntity();
            //Copy Value
            newField.setCreatedAt(ZonedDateTime.now());
            newField.setUpdatedAt(ZonedDateTime.now());
            newField.setEntityVersion(targetVersion);
            newField.setName(entityFieldEntity.getName());
            newField.setDisplayName(entityFieldEntity.getDisplayName());
            newField.setDataType(entityFieldEntity.getDataType());
            newField.setProperties(entityFieldEntity.getProperties());
            newField.setRequired(entityFieldEntity.getRequired());
            newField.setSearchable(entityFieldEntity.getSearchable());
            newField.setDescription(entityFieldEntity.getDescription());
            newField.setPosition(entityFieldEntity.getPosition());
            newField.setUnique(entityFieldEntity.getUnique());
            newField.setPermanentId(entityFieldEntity.getPermanentId());
            newField.setJavaType(entityFieldEntity.getJavaType());
            newField.setColumn(entityFieldEntity.getColumn());
            newField.setInputType(entityFieldEntity.getInputType());

            this.entityFieldRepository.save(newField);
        }

        //when the source target is flagged as deleted (commit on DELETE action), do not update the parent entity
        if (sourceVersion.getDeleted() == null || sourceVersion.getDeleted()) {
            EntityEntity entityEntity = targetVersion.getEntity();
            entityEntity.setName(sourceVersion.getName());
            entityEntity.setDisplayName(sourceVersion.getDisplayName());
            entityEntity.setDescription(sourceVersion.getDescription());
            entityEntity.setDeletedAt(null);
            this.entityRepository.save(entityEntity);
        }

        //update permission
        List<EntityPermissionEntity> sourcePermissionEntities =
                this.entityPermissionRepository.getPermissions(sourceVersion.getId());
        for (EntityPermissionEntity entityPermissionEntity : sourcePermissionEntities) {
            EntityPermissionEntity permissionEntity = new EntityPermissionEntity();
            permissionEntity.setAction(entityPermissionEntity.getAction());
            permissionEntity.setType(entityPermissionEntity.getType());
            permissionEntity.setEntityVersion(targetVersion);
            this.entityPermissionRepository.save(permissionEntity);

            entityPermissionEntity.getPermissionRoles().forEach(entityPermissionRoleEntity -> {
                EntityPermissionRoleEntity permissionRoleEntity = new EntityPermissionRoleEntity();
                permissionRoleEntity.setAppRole(entityPermissionRoleEntity.getAppRole());
                permissionRoleEntity.setEntityPermission(permissionEntity);
                this.entityPermissionRoleRepository.save(permissionRoleEntity);
            });
        }
        this.entityPermissionRoleRepository.flush();
        //update permission field
        for (EntityPermissionEntity permissionEntity : sourcePermissionEntities) {
            List<EntityPermissionEntity> entityPermissionEntities = this.entityPermissionRepository.getEntitiesByActionAndVersion(permissionEntity.getAction(),
                    targetVersionId);

            for (EntityPermissionEntity updEntityPermission : entityPermissionEntities) {
                List<EntityPermissionFieldEntity> permissionFields = permissionEntity.getPermissionFields();
                for (EntityPermissionFieldEntity permissionField : permissionFields) {
                    EntityPermissionFieldEntity newPermissionFieldEntity = new EntityPermissionFieldEntity();

                    EntityFieldEntity entityFieldEntity = this.entityFieldRepository.findByVersionAndPermanentId(targetVersionId, permissionField.getField().getPermanentId())
                            .orElseThrow(MetaException::new);

                    newPermissionFieldEntity.setField(entityFieldEntity);
                    // newPermissionFieldEntity.setEntityVersionId(targetVersionId);
                    // newPermissionFieldEntity.setFieldPermanentId(permissionField.getFieldPermanentId());
                    newPermissionFieldEntity.setPermission(updEntityPermission);
                    //TODO bug here.
                    this.entityPermissionFieldRepository.save(newPermissionFieldEntity);

                    Set<EntityPermissionRoleEntity> permissionRoles = permissionField.getPermissionRoles();
                    for (EntityPermissionRoleEntity permissionRole : permissionRoles) {
                        EntityPermissionRoleEntity newPermissionRole = new EntityPermissionRoleEntity();
                        newPermissionRole.setEntityVersionId(targetVersionId);
                        newPermissionRole.setAction(permissionRole.getAction());
                        newPermissionRole.setAppRole(permissionRole.getAppRole());
                        newPermissionRole.setEntityPermission(updEntityPermission);
                        this.entityPermissionRoleRepository.save(newPermissionRole);
                    }
                }
            }

        }

        targetVersion = this.entityVersionRepository.getById(targetVersionId);


        return this.entityVersionMapper.toDto(targetVersion);
    }
}
