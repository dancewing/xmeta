package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AppRoleEntity;
import io.xmeta.graphql.domain.AppRoleEntity_;
import io.xmeta.graphql.mapper.AppRoleMapper;
import io.xmeta.graphql.model.AppRole;
import io.xmeta.graphql.model.AppRoleOrderByInput;
import io.xmeta.graphql.model.AppRoleWhereInput;
import io.xmeta.graphql.repository.AppRoleRepository;
import io.xmeta.graphql.util.PredicateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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
@Transactional
public class AppRoleService extends BaseService<AppRoleRepository, AppRoleEntity, String> {

    private final AppRoleRepository appRoleRepository;
    private final AppRoleMapper appRoleMapper;

    public AppRoleService(AppRoleRepository appRoleRepository, AppRoleMapper appRoleMapper) {
        super(appRoleRepository);
        this.appRoleRepository = appRoleRepository;
        this.appRoleMapper = appRoleMapper;
    }

    public List<AppRole> appRoles(AppRoleWhereInput where, AppRoleOrderByInput orderBy, Integer skip, Integer take) {
        Specification<AppRoleEntity> specification = Specification.where(null);
        Specification<AppRoleEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (where != null) {
                if (where.getId() != null) {
                    predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, root.get(AppRoleEntity_.ID),
                            where.getId()));
                }
                if (where.getName() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder, root.get(AppRoleEntity_.NAME),
                            where.getName()));
                }
                if (where.getDisplayName() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder, root.get(AppRoleEntity_.DISPLAY_NAME),
                            where.getDisplayName()));
                }
                if (where.getDescription() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder, root.get(AppRoleEntity_.DESCRIPTION),
                            where.getDescription()));
                }
                if (where.getCreatedAt() != null) {
                    predicates.addAll(PredicateBuilder.buildDateTimeFilter(criteriaBuilder, root.get(AppRoleEntity_.CREATED_AT),
                            where.getCreatedAt()));
                }
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<AppRoleEntity> result = null;
        if (skip != null && take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.appRoleRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.appRoleRepository.findAll(specification, sort);
        }
        return result.stream().map(this.appRoleMapper::toDto).collect(Collectors.toList());
    }
}
