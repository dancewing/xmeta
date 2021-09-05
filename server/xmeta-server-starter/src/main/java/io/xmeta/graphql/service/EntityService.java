package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.domain.EntityEntity;
import io.xmeta.graphql.domain.EntityFieldEntity;
import io.xmeta.graphql.domain.EntityVersionEntity;
import io.xmeta.graphql.mapper.EntityMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.EntityFieldRepository;
import io.xmeta.graphql.repository.EntityRepository;
import io.xmeta.graphql.repository.EntityVersionRepository;
import io.xmeta.graphql.util.Inflector;
import io.xmeta.security.AuthUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class EntityService extends BaseService<EntityRepository, EntityEntity, String> {

    private final EntityRepository entityRepository;
    private final EntityFieldRepository entityFieldRepository;
    private final EntityVersionRepository entityVersionRepository;
    private final EntityMapper entityMapper;

    public EntityService(EntityRepository entityRepository, EntityFieldRepository entityFieldRepository, EntityVersionRepository entityVersionRepository, EntityMapper entityMapper) {
        super(entityRepository);
        this.entityRepository = entityRepository;
        this.entityFieldRepository = entityFieldRepository;
        this.entityVersionRepository = entityVersionRepository;
        this.entityMapper = entityMapper;
    }

    public List<Entity> entities(EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        Specification<EntityEntity> specification = Specification.where(null);
        Specification<EntityEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (where != null) {
                predicates.addAll(createPredicates(where, root, criteriaBuilder));
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

    @Transactional
    public void createEntities(AuthUser authUser, AppEntity appEntity, List<AppCreateWithEntitiesEntityInput> entities) {
        if (entities == null) {
            return;
        }
        entities.stream().forEach(appCreateWithEntitiesEntityInput -> {
            String displayName = StringUtils.trim(appCreateWithEntitiesEntityInput.getName());

            String pluralDisplayName = Inflector.getInstance().pluralize(displayName);
            String singularDisplayName = Inflector.getInstance().singularize(displayName);
            String name = Inflector.getInstance().lowerCamelCase(singularDisplayName);

            EntityEntity entityEntity = new EntityEntity();
            entityEntity.setCreatedAt(ZonedDateTime.now());
            entityEntity.setUpdatedAt(ZonedDateTime.now());
            entityEntity.setAppId(appEntity.getId());
            entityEntity.setName(name);
            entityEntity.setDisplayName(displayName);
            entityEntity.setPluralDisplayName(pluralDisplayName);
            entityEntity.setDescription(null);
            entityEntity.setLockedByUserId(authUser.getUserId());
            entityEntity.setLockedAt(ZonedDateTime.now());
            //   entityEntity.setDeletedAt(ZonedDateTime.now());
            this.entityRepository.save(entityEntity);

            //create Version

            EntityVersionEntity entityVersionEntity = new EntityVersionEntity();
            entityVersionEntity.setCreatedAt(ZonedDateTime.now());
            entityVersionEntity.setUpdatedAt(ZonedDateTime.now());
            entityVersionEntity.setEntityId(entityEntity.getId());
            entityVersionEntity.setVersionNumber(1L);
            entityVersionEntity.setName(name);
            entityVersionEntity.setDisplayName(displayName);
            entityVersionEntity.setPluralDisplayName(pluralDisplayName);
            entityVersionEntity.setDescription(null);
            entityVersionEntity.setCommitId(null);
            entityVersionEntity.setDeleted(null);
            //entityVersionEntity.setBuilds(Sets.newHashSet());
            this.entityVersionRepository.save(entityVersionEntity);

            List<AppCreateWithEntitiesFieldInput> fields = appCreateWithEntitiesEntityInput.getFields();
            if (fields != null) {
                fields.stream().forEach(appCreateWithEntitiesFieldInput -> {
                    EntityFieldEntity entityFieldEntity = new EntityFieldEntity();
                    entityFieldEntity.setCreatedAt(ZonedDateTime.now());
                    entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
                    entityFieldEntity.setEntityVersionId(entityVersionEntity.getId());
                    entityFieldEntity.setPermanentId("");
                    entityFieldEntity.setName(appCreateWithEntitiesFieldInput.getName());
                    entityFieldEntity.setDisplayName("");
                    entityFieldEntity.setDataType(appCreateWithEntitiesFieldInput.getDataType().name());
                    entityFieldEntity.setProperties(null);
                    entityFieldEntity.setRequired(false);
                    entityFieldEntity.setSearchable(false);
                    entityFieldEntity.setDescription(null);
                    entityFieldEntity.setPosition(0L);
                    entityFieldEntity.setUnique(false);
                    this.entityFieldRepository.save(entityFieldEntity);

                });
            }
        });
    }
}
