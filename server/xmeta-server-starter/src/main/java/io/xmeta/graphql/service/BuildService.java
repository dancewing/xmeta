package io.xmeta.graphql.service;

import io.xmeta.graphql.mapper.BuildMapper;
import io.xmeta.graphql.model.App;
import io.xmeta.graphql.model.Build;
import io.xmeta.graphql.model.BuildOrderByInput;
import io.xmeta.graphql.model.BuildWhereInput;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.BuildEntity;
import io.xmeta.graphql.repository.BuildRepository;

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
public class BuildService extends BaseService<BuildRepository, BuildEntity, String> {

    private final BuildRepository buildRepository;
    private final BuildMapper buildMapper;

    public BuildService(BuildRepository buildRepository, BuildMapper buildMapper) {
        super(buildRepository);
        this.buildRepository = buildRepository;
        this.buildMapper = buildMapper;
    }

    public List<Build> builds(App app, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        Specification<BuildEntity> specification = Specification.where(null);
        Specification<BuildEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (where != null) {
                predicates.addAll(createPredicates(where, root, criteriaBuilder));
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<BuildEntity> result = null;
        if (skip != null && take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.buildRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.buildRepository.findAll(specification, sort);
        }
        return result.stream().map(this.buildMapper::toDto).collect(Collectors.toList());
    }
}
