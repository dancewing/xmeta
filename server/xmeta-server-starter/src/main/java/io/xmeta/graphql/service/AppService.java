package io.xmeta.graphql.service;
import java.time.ZonedDateTime;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.domain.AppEntity_;
import io.xmeta.graphql.domain.AppRoleEntity;
import io.xmeta.graphql.domain.WorkspaceEntity;
import io.xmeta.graphql.mapper.AppMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.AppRepository;
import io.xmeta.graphql.repository.AppRoleRepository;
import io.xmeta.graphql.util.Inflector;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.apache.commons.lang.StringUtils;
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
    private final AppRoleRepository appRoleRepository;
    private final AppMapper appMapper;
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final BlockService blockService;
    private final CommitService commitService;
    private final EntityVersionService entityVersionService;

    public AppService(AppRepository appRepository, AppRoleRepository appRoleRepository, AppMapper appMapper, EntityService entityService, EnvironmentService environmentService, BlockService blockService, CommitService commitService, EntityVersionService entityVersionService) {
        super(appRepository);
        this.appRepository = appRepository;
        this.appRoleRepository = appRoleRepository;
        this.appMapper = appMapper;
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.blockService = blockService;
        this.commitService = commitService;
        this.entityVersionService = entityVersionService;
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
            // 过滤非删除app
            predicates.add(criteriaBuilder.isNull(root.get(AppEntity_.DELETED_AT)));

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<AppEntity> result = null;
        if (skip != null && take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.appRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.appRepository.findAll(specification, sort);
        }
        return result.stream().map(this.appMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public App createApp(AppCreateInput data) {
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();

        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.setId(authUserDetail.getWorkspaceId());

        AppEntity appEntity = new AppEntity();
        appEntity.setCreatedAt(ZonedDateTime.now());
        appEntity.setUpdatedAt(ZonedDateTime.now());
        appEntity.setWorkspace(workspaceEntity);
        appEntity.setName(data.getName());
        appEntity.setDescription(data.getDescription());
        appEntity.setColor(data.getColor());
        appEntity.setGithubToken("");
        appEntity.setGithubTokenCreatedDate(ZonedDateTime.now());
        appEntity.setGithubSyncEnabled(false);
        appEntity.setGithubRepo("");
        appEntity.setGithubBranch("");
        appEntity.setGithubLastSync(ZonedDateTime.now());
        appEntity.setGithubLastMessage("");
//        appEntity.setDeletedAt(new Date());

        //app 名称重复，自动加后缀
        this.appRepository.saveAndFlush(appEntity);

        //创建 approle
        AppRoleEntity appRoleEntity = new AppRoleEntity();
        appRoleEntity.setApp(appEntity);
        appRoleEntity.setName("user");
        appRoleEntity.setDisplayName("User");
        appRoleEntity.setCreatedAt(ZonedDateTime.now());
        appRoleEntity.setUpdatedAt(ZonedDateTime.now());
        this.appRoleRepository.save(appRoleEntity);

        this.entityService.createDefaultEntities(appEntity.getId(), authUserDetail.getUserId());

        this.environmentService.createDefaultEnvironment(appEntity.getId());

        return this.appMapper.toDto(appEntity);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public App createAppWithEntities(AppCreateWithEntitiesInput data) {
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        App app = this.createApp(new AppCreateInput(data.getApp().getName(), data.getApp().getDescription(), data.getApp().getColor()));
        //app 名称重复，自动加后缀
        List<AppCreateWithEntitiesEntityInput> entities = data.getEntities();

        if (entities !=null && entities.size()>0){
            entities.forEach(appCreateWithEntitiesEntityInput -> {

                String displayName = StringUtils.trim(appCreateWithEntitiesEntityInput.getName());
                String pluralDisplayName = Inflector.getInstance().pluralize(displayName);
                String singularDisplayName = Inflector.getInstance().singularize(displayName);
                String name = Inflector.getInstance().lowerCamelCase(singularDisplayName);

                EntityCreateInput.Builder builder = EntityCreateInput.builder();
                builder.setApp(WhereParentIdInput.builder().setConnect(WhereUniqueInput.builder().setId(app.getId()).build()).build());
                builder.setName(name);
                builder.setDisplayName(displayName);
                builder.setPluralDisplayName(pluralDisplayName);
                Entity newEntity = this.entityService.createOneEntity(builder.build());
                List<AppCreateWithEntitiesFieldInput> fields = appCreateWithEntitiesEntityInput.getFields();
                if (fields!=null && fields.size()>0) {
                    fields.forEach(appCreateWithEntitiesFieldInput -> {
                        this.entityService.createFieldByDisplayName(newEntity,
                                appCreateWithEntitiesFieldInput.getName(),
                                appCreateWithEntitiesFieldInput.getDataType(),
                                authUserDetail.getUserId());
                    });
                }
                // create relation //TODO
            });
            this.commit(new CommitCreateInput(data.getCommitMessage(), new WhereParentIdInput(new WhereUniqueInput(app.getId()))));
        }

        return app;
    }

    private void commit(CommitCreateInput commitCreateInput) {
        // CHECK IF APP and  EXISTS
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        String appId = commitCreateInput.getApp().getConnect().getId();
        String userId = authUserDetail.getUserId();

        List<PendingChange> changedEntities = this.entityService.getChangedEntities(appId, userId);
        List<PendingChange> changedBlocks = this.entityService.getChangedEntities(appId, userId);

        Commit commit = this.commitService.commit(commitCreateInput);

        changedEntities.forEach(pendingChange -> {
            this.entityVersionService.createVersion(pendingChange.getResourceId(), commit.getId());
        });

    }

    @Transactional
    public App deleteApp(WhereUniqueInput where) {
        AppEntity appEntity = this.appRepository.getById(where.getId());
        appEntity.setName("__"+appEntity.getId()+"__"+ appEntity.getName());
        appEntity.setDeletedAt(ZonedDateTime.now());
        this.appRepository.save(appEntity);
        return this.appMapper.toDto(appEntity);
    }

    @Transactional
    public App updateApp(AppUpdateInput data, WhereUniqueInput where) {
        AppEntity appEntity = this.appRepository.getById(where.getId());
        appEntity.setUpdatedAt(ZonedDateTime.now());
        appEntity.setName(data.getName());
        appEntity.setDescription(data.getDescription());
        appEntity.setColor(data.getColor());
        this.appRepository.save(appEntity);
        return this.appMapper.toDto(appEntity);
    }

    public List<PendingChange> pendingChanges(PendingChangesFindInput where) {
        AppEntity appEntity = this.appRepository.getById(where.getApp().getId());
        if (appEntity == null) {
            throw new RuntimeException("");
        }
        String appId = where.getApp().getId();
        String userId = SecurityUtils.getAuthUser().getUserId();
        List<PendingChange> pendingChanges = this.entityService.getChangedEntities(appId, userId);

        pendingChanges.addAll(this.blockService.getChangedBlocks(appId, userId));

        return pendingChanges;
    }
}
