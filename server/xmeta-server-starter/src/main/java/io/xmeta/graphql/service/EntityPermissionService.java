package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.EntityPermissionMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.EntityPermissionRepository;
import io.xmeta.graphql.repository.EntityVersionRepository;
import io.xmeta.graphql.util.PredicateBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@PreAuthorize("isAuthenticated()")
public class EntityPermissionService extends BaseService<EntityPermissionRepository, EntityPermissionEntity, String> {

    private final EntityPermissionRepository permissionRepository;
    private final EntityPermissionMapper entityPermissionMapper;
    private final LockService lockService;
    private final EntityVersionRepository entityVersionRepository;

    public EntityPermissionService(EntityPermissionRepository permissionRepository, EntityPermissionMapper entityPermissionMapper, LockService lockService, EntityVersionRepository entityVersionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
        this.entityPermissionMapper = entityPermissionMapper;
        this.lockService = lockService;
        this.entityVersionRepository = entityVersionRepository;
    }

    @Transactional
    public void createEntityPermission(String action, String type, EntityVersionEntity entityVersionEntity) {
        EntityPermissionEntity entityPermission = new EntityPermissionEntity();
        entityPermission.setEntityVersion(entityVersionEntity);
        entityPermission.setAction(action);
        entityPermission.setType(type);
        this.permissionRepository.save(entityPermission);
    }

    public List<EntityPermission> getPermissions(String entityId, EnumEntityAction action) {
        return this.getVersionPermissions(entityId, 0, action);
    }

    public List<EntityPermission> getVersionPermissions(String entityId,
                                                        Integer versionNumber, EnumEntityAction action) {
        Specification<EntityPermissionEntity> specification = Specification.where(null);
        Specification<EntityPermissionEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entityId != null && versionNumber != null) {
                Join<Object, Object> join = root.join(EntityPermissionEntity_.ENTITY_VERSION, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(EntityVersionEntity_.ENTITY_ID),
                        entityId));
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(EntityVersionEntity_.VERSION_NUMBER),
                        versionNumber));

                Join<Object, Object> subJoin = join.join(EntityVersionEntity_.ENTITY, JoinType.LEFT);
                predicates.add(PredicateBuilder.byFieldSpecified(criteriaBuilder, subJoin.get(EntityEntity_.DELETED_AT),
                        false));
            }

            if (action != null) {
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder,
                        root.get(EntityPermissionEntity_.ACTION),
                        action));
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = Sort.by(Sort.Order.asc("action"));

        List<EntityPermissionEntity> result = this.permissionRepository.findAll(specification, sort);
        ;

        return this.entityPermissionMapper.toDto(result);
    }

    @Transactional
    public EntityPermission updateEntityPermission(EntityUpdatePermissionInput data, WhereUniqueInput where) {
        EntityEntity entityEntity = this.lockService.acquireEntityLock(where.getId());
        EntityVersionEntity entityVersion = this.entityVersionRepository.findEntityVersion(entityEntity.getId(), 0);
        if (entityVersion == null) {
            throw new RuntimeException("can't find entity version");
        }
        List<EntityPermissionEntity> permissionEntities = this.permissionRepository.getEntitiesByActionAndVersion(data.getAction().name(), entityVersion.getId());
        if (permissionEntities.size() > 1) {
            throw new RuntimeException("EntityVersion has more than one permission version");
        }
        EntityPermissionEntity permissionEntity = null;
        if (permissionEntities.size() == 0) {
            permissionEntity = new EntityPermissionEntity();
            permissionEntity.setEntityVersion(entityVersion);
            permissionEntity.setAction(data.getAction().name());
        } else {
            permissionEntity = permissionEntities.get(0);
        }
        permissionEntity.setType(data.getType().name());
        permissionRepository.save(permissionEntity);
        return this.entityPermissionMapper.toDto(permissionEntity);
    }

    @Transactional
    public EntityPermission updateEntityPermissionRoles(EntityUpdatePermissionRolesInput data) {
        EntityEntity entityEntity = this.lockService.acquireEntityLock(data.getEntity().getConnect().getId());
        EntityVersionEntity entityVersion = this.entityVersionRepository.findEntityVersion(entityEntity.getId(), 0);
        if (entityVersion == null) {
            throw new RuntimeException("can't find entity version");
        }

        return null;
    }

}
