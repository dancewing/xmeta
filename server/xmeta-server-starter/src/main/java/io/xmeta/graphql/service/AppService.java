package io.xmeta.graphql.service;
import java.time.ZonedDateTime;
import java.util.Date;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.mapper.AppMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.AppRepository;
import io.xmeta.security.AuthUser;
import io.xmeta.security.SecurityUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
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
public class AppService extends BaseService<AppRepository, AppEntity, String> {

    private final AppRepository appRepository;
    private final AppMapper appMapper;
    private final EntityService entityService;

    public AppService(AppRepository appRepository, AppMapper appMapper, EntityService entityService) {
        super(appRepository);
        this.appRepository = appRepository;
        this.appMapper = appMapper;
        this.entityService = entityService;
    }

    public App app(WhereUniqueInput where) {
        return this.appMapper.toDto(this.appRepository.getById(where.getId()));
    }

    public List<App> apps(AppWhereInput where, AppOrderByInput orderBy, Integer skip, Integer take) {
        Specification<AppEntity> specification = Specification.where(null);
        Specification<AppEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (where != null) {
                predicates.addAll(createPredicates(where, root, criteriaBuilder));
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<AppEntity> result = null;
        if (skip != null && take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.appRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.appRepository.findAll(sort);
        }
        return result.stream().map(this.appMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public App createAppWithEntities(AppCreateWithEntitiesInput data) {
        AuthUser authUser = SecurityUtils.getAuthUser();
        AppEntity appEntity = new AppEntity();
        appEntity.setCreatedAt(ZonedDateTime.now());
        appEntity.setUpdatedAt(ZonedDateTime.now());
        appEntity.setWorkspaceId(authUser.getWorkspaceId());
        appEntity.setName(data.getApp().getName());
        appEntity.setDescription(data.getApp().getDescription());
        appEntity.setColor(data.getApp().getColor());
        appEntity.setGithubToken("");
        appEntity.setGithubTokenCreatedDate(ZonedDateTime.now());
        appEntity.setGithubSyncEnabled(false);
        appEntity.setGithubRepo("");
        appEntity.setGithubBranch("");
        appEntity.setGithubLastSync(ZonedDateTime.now());
        appEntity.setGithubLastMessage("");
//        appEntity.setDeletedAt(new Date());
        this.appRepository.save(appEntity);

        //app 名称重复，自动加后缀

        this.entityService.createEntities(authUser, appEntity, data.getEntities());


        return this.appMapper.toDto(appEntity);
    }
}
