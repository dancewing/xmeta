package io.xmeta.graphql.service;

import io.xmeta.graphql.mapper.EntityVersionMapper;
import io.xmeta.graphql.model.Entity;
import io.xmeta.graphql.model.EntityVersion;
import io.xmeta.graphql.model.EntityVersionOrderByInput;
import io.xmeta.graphql.model.EntityVersionWhereInput;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.EntityVersionEntity;
import io.xmeta.graphql.repository.EntityVersionRepository;

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

    private final EntityVersionRepository versionRepository;
    private final EntityVersionMapper entityVersionMapper;

    public EntityVersionService(EntityVersionRepository versionRepository, EntityVersionMapper entityVersionMapper) {
        super(versionRepository);
        this.versionRepository = versionRepository;
        this.entityVersionMapper = entityVersionMapper;
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
            result = this.versionRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.versionRepository.findAll(sort);
        }
        return result.stream().map(this.entityVersionMapper::toDto).collect(Collectors.toList());
    }
}
