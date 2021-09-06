package io.xmeta.graphql.service;
import java.time.ZonedDateTime;
import io.xmeta.graphql.domain.EntityEntity;
import io.xmeta.graphql.domain.CommitEntity;

import io.xmeta.graphql.mapper.EntityVersionMapper;
import io.xmeta.graphql.model.Entity;
import io.xmeta.graphql.model.EntityVersion;
import io.xmeta.graphql.model.EntityVersionOrderByInput;
import io.xmeta.graphql.model.EntityVersionWhereInput;
import io.xmeta.graphql.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.EntityVersionEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
public class EntityVersionService extends BaseService<EntityVersionRepository, EntityVersionEntity, String> {

    private final EntityVersionRepository entityVersionRepository;
    private final EntityVersionMapper entityVersionMapper;
    private final EntityRepository entityRepository;
    private final EntityPermissionFieldRepository entityPermissionFieldRepository;
    private final EntityPermissionRoleRepository entityPermissionRoleRepository;
    private final EntityFieldRepository entityFieldRepository;
    private final EntityPermissionRepository entityPermissionRepository;

    public EntityVersionService(EntityVersionRepository entityVersionRepository, EntityVersionMapper entityVersionMapper, EntityRepository entityRepository, EntityPermissionFieldRepository entityPermissionFieldRepository, EntityPermissionRoleRepository entityPermissionRoleRepository, EntityFieldRepository entityFieldRepository, EntityPermissionRepository entityPermissionRepository) {
        super(entityVersionRepository);
        this.entityVersionRepository = entityVersionRepository;
        this.entityVersionMapper = entityVersionMapper;
        this.entityRepository = entityRepository;
        this.entityPermissionFieldRepository = entityPermissionFieldRepository;
        this.entityPermissionRoleRepository = entityPermissionRoleRepository;
        this.entityFieldRepository = entityFieldRepository;
        this.entityPermissionRepository = entityPermissionRepository;
    }

    public List<EntityVersion> versions(Entity entity, EntityVersionWhereInput where, EntityVersionOrderByInput orderBy, Integer skip, Integer take) {
        Specification<EntityVersionEntity> specification = Specification.where(null);
        Specification<EntityVersionEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (where != null) {
                predicates.addAll(createPredicates(where, root, criteriaBuilder));
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<EntityVersionEntity> result = null;
        if (skip != null && take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.entityVersionRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.entityVersionRepository.findAll(sort);
        }
        return result.stream().map(this.entityVersionMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public EntityVersion createVersion(String entityId, String commitId) {
        EntityEntity entityEntity = this.entityRepository.getById(entityId);
        List<EntityVersionEntity> entityVersions = this.entityVersionRepository.findEntityVersions(entityId);
        if (entityVersions.size() == 0) {
            throw new RuntimeException("Entity "+ entityId +" has no versions");
        }
        EntityVersionEntity firstEntityVersion = entityVersions.get(0);
        EntityVersionEntity lastEntityVersion =  entityVersions.get(entityVersions.size()-1);
            Integer lastVersionNumber = lastEntityVersion.getVersionNumber();

        Integer nextVersionNumber = lastVersionNumber + 1;
        EntityVersionEntity newEntityVersion = new EntityVersionEntity();
        newEntityVersion.setCreatedAt(ZonedDateTime.now());
        newEntityVersion.setUpdatedAt(ZonedDateTime.now());
        newEntityVersion.setEntity(entityEntity);
        newEntityVersion.setVersionNumber(nextVersionNumber);
        newEntityVersion.setName(firstEntityVersion.getName());
        newEntityVersion.setDisplayName(firstEntityVersion.getDisplayName());
        newEntityVersion.setPluralDisplayName(firstEntityVersion.getPluralDisplayName());
        newEntityVersion.setDescription(firstEntityVersion.getDescription());

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

        return null;
    }
}
