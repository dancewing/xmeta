package io.xmeta.admin.service;

import io.xmeta.admin.domain.AppEntity;
import io.xmeta.graphql.domain.AppEntity_;
import io.xmeta.admin.domain.AppRoleEntity;
import io.xmeta.graphql.domain.AppRoleEntity_;
import io.xmeta.admin.mapper.AppRoleMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.admin.repository.AppRoleRepository;
import io.xmeta.admin.util.PredicateBuilder;
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
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional
@PreAuthorize("isAuthenticated()")
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
                if (where.getApp() != null && where.getApp().getId() != null) {
                    Join<Object, Object> join = root.join(AppRoleEntity_.APP, JoinType.LEFT);
                    predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), where.getApp().getId()));
                }
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
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.appRoleRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.appRoleRepository.findAll(specification, sort);
        }
        return result.stream().map(this.appRoleMapper::toDto).collect(Collectors.toList());
    }

    public AppRole appRole(WhereUniqueInput where, Double version) {
        return this.appRoleMapper.toDto(getById(where.getId()));
    }

    @Transactional(readOnly = false)
    public AppRole createAppRole(AppRoleCreateInput data) {
        AppRoleEntity appRoleEntity = new AppRoleEntity();
        appRoleEntity.setCreatedAt(ZonedDateTime.now());
        appRoleEntity.setUpdatedAt(ZonedDateTime.now());
        AppEntity appEntity = new AppEntity();
        appEntity.setId(data.getApp().getConnect().getId());
        appRoleEntity.setApp(appEntity);
        appRoleEntity.setName(data.getName());
        appRoleEntity.setDisplayName(data.getDisplayName());
        appRoleEntity.setDescription(data.getDescription());

        return this.appRoleMapper.toDto(this.appRoleRepository.save(appRoleEntity));
    }

    @Transactional(readOnly = false)
    public AppRole deleteAppRole(WhereUniqueInput where) {
        AppRoleEntity appRoleEntity = this.appRoleRepository.getById(where.getId());
        this.appRoleRepository.deleteById(where.getId());
        return this.appRoleMapper.toDto(appRoleEntity);
    }

    @Transactional(readOnly = false)
    public AppRole updateAppRole(AppRoleUpdateInput data, WhereUniqueInput where) {
        AppRoleEntity appRoleEntity = this.appRoleRepository.getById(where.getId());
        appRoleEntity.setUpdatedAt(ZonedDateTime.now());
        appRoleEntity.setName(data.getName());
        appRoleEntity.setDisplayName(data.getDisplayName());
        appRoleEntity.setDescription(data.getDescription());
        return this.appRoleMapper.toDto(this.appRoleRepository.save(appRoleEntity));
    }
}
