package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.BuildMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.util.PredicateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.repository.BuildRepository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

    public List<Build> builds(App app, Commit commit, BuildWhereInput where, BuildOrderByInput orderBy, Integer take,
                              Integer skip) {
        Specification<BuildEntity> specification = Specification.where(null);
        Specification<BuildEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (app!=null && app.getId()!=null){
                Join<Object, Object> join = root.join(BuildEntity_.APP, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), app.getId()));
            }
            if (commit!=null && commit.getId()!=null){
                Join<Object, Object> join = root.join(BuildEntity_.COMMIT, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(CommitEntity_.ID), commit.getId()));
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
