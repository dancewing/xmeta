package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.AppMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.AppRepository;
import io.xmeta.graphql.repository.AppRoleRepository;
import io.xmeta.graphql.util.Inflector;
import io.xmeta.graphql.util.PredicateBuilder;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@Slf4j
public class AppService extends BaseService<AppRepository, AppEntity, String> {

    private final AppRepository appRepository;
    private final AppRoleRepository appRoleRepository;
    private final AppMapper appMapper;
    private final EntityService entityService;
    private final EnvironmentService environmentService;
    private final BlockService blockService;
    private final BlockVersionService blockVersionService;
    private final CommitService commitService;
    private final EntityVersionService entityVersionService;
    private final EntityFieldService entityFieldService;
    private final BuildService buildService;

    public AppService(AppRepository appRepository, AppRoleRepository appRoleRepository, AppMapper appMapper, EntityService entityService, EnvironmentService environmentService, BlockService blockService, BlockVersionService blockVersionService, CommitService commitService, EntityVersionService entityVersionService, EntityFieldService entityFieldService, BuildService buildService) {
        super(appRepository);
        this.appRepository = appRepository;
        this.appRoleRepository = appRoleRepository;
        this.appMapper = appMapper;
        this.entityService = entityService;
        this.environmentService = environmentService;
        this.blockService = blockService;
        this.blockVersionService = blockVersionService;
        this.commitService = commitService;
        this.entityVersionService = entityVersionService;
        this.entityFieldService = entityFieldService;
        this.buildService = buildService;
    }

    public App app(WhereUniqueInput where) {
        return this.appMapper.toDto(this.appRepository.getById(where.getId()));
    }

    public List<App> apps(AppWhereInput where, AppOrderByInput orderBy, Integer skip, Integer take) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        Specification<AppEntity> specification = Specification.where(null);
        Specification<AppEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //只查找当前workspace的
            Join<Object, Object> join = root.join(AppEntity_.WORKSPACE, JoinType.LEFT);
            predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(WorkspaceEntity_.ID),
                    authUser.getWorkspaceId()));
            // 过滤非删除app
            predicates.add(criteriaBuilder.isNull(root.get(AppEntity_.DELETED_AT)));

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<AppEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.appRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.appRepository.findAll(specification, sort);
        }
        return result.stream().map(this.appMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public App createApp(AppCreateInput data) {
        // 重名，自动改名
        String name = data.getName();
        List<AppEntity> existingApps = this.findExistingApps(name);
        int index = 1;
        while (appNameFound(existingApps, name)) {
            name = name + "-" + index;
            index += 1;
        }

        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();

        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.setId(authUserDetail.getWorkspaceId());

        AppEntity appEntity = new AppEntity();
        appEntity.setCreatedAt(ZonedDateTime.now());
        appEntity.setUpdatedAt(ZonedDateTime.now());
        appEntity.setWorkspace(workspaceEntity);
        appEntity.setName(name);
        appEntity.setDescription(data.getDescription());
        appEntity.setColor(data.getColor());
        appEntity.setGithubToken("");
        appEntity.setGithubTokenCreatedDate(ZonedDateTime.now());
        appEntity.setGithubSyncEnabled(false);
        appEntity.setGithubRepo("");
        appEntity.setGithubBranch("");
        appEntity.setGithubLastSync(ZonedDateTime.now());
        appEntity.setGithubLastMessage("");

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

        this.commit(new CommitCreateInput("INITIAL_COMMIT_MESSAGE",
                new WhereParentIdInput(new WhereUniqueInput(appEntity.getId()))), true);

        return this.appMapper.toDto(appEntity);
    }

    private boolean appNameFound(List<AppEntity> existingApps, String name) {
        return existingApps.stream().filter(appEntity -> StringUtils.equalsIgnoreCase(appEntity.getName(),
                        name))
                .collect(Collectors.toList()).size() > 0;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public App createAppWithEntities(AppCreateWithEntitiesInput data) {
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        App app = this.createApp(new AppCreateInput(data.getApp().getName(), data.getApp().getDescription(), data.getApp().getColor()));
        List<AppCreateWithEntitiesEntityInput> entities = data.getEntities();

        List<Entity> newEntities = new ArrayList<>();
        if (entities != null && entities.size() > 0) {
            entities.forEach(appEntityInput -> {
                String displayName = StringUtils.trim(appEntityInput.getName());
                String pluralDisplayName = Inflector.getInstance().pluralize(displayName);
                String singularDisplayName = Inflector.getInstance().singularize(displayName);
                String name = Inflector.getInstance().lowerCamelCase(singularDisplayName, ' ');

                EntityCreateInput.Builder builder = EntityCreateInput.builder();
                builder.setApp(WhereParentIdInput.builder().setConnect(WhereUniqueInput.builder().setId(app.getId()).build()).build());
                builder.setName(name);
                builder.setDisplayName(displayName);
                builder.setPluralDisplayName(pluralDisplayName);
                Entity newEntity = this.entityService.createOneEntity(builder.build());
                newEntities.add(newEntity);

                List<AppCreateWithEntitiesFieldInput> fields = appEntityInput.getFields();

                if (fields != null && fields.size() > 0) {
                    fields.forEach(appCreateWithEntitiesFieldInput -> {
                        this.entityFieldService.createEntityFieldByDisplayName(createFieldByDisplayName(newEntity,
                                appCreateWithEntitiesFieldInput.getName(),
                                appCreateWithEntitiesFieldInput.getDataType(),
                                authUserDetail.getUserId()));
                    });
                }
            });

            int entityIndex = 0;
            for (AppCreateWithEntitiesEntityInput appEntityInput : entities) {
                if (appEntityInput.getRelationsToEntityIndex() != null && appEntityInput.getRelationsToEntityIndex().size() > 0) {
                    for (Integer relationToIndex : appEntityInput.getRelationsToEntityIndex()) {
                        if (relationToIndex < 0 || relationToIndex >= newEntities.size()) {
                            throw new RuntimeException("wrong RelationsToEntityIndex");
                        }
                        Entity relationEntity = newEntities.get(relationToIndex);
                        this.entityFieldService.createEntityFieldByDisplayName(createFieldByDisplayName(newEntities.get(entityIndex),
                                relationEntity.getDisplayName(),
                                EnumDataType.Lookup,
                                authUserDetail.getUserId()));
                    }
                } else {
                    log.info("no relation field");
                }
                entityIndex++;
            }
            //flush all changes
            this.appRepository.flush();
            this.commit(new CommitCreateInput(data.getCommitMessage(),
                    new WhereParentIdInput(new WhereUniqueInput(app.getId()))), false);
        }

        return app;
    }

    private List<AppEntity> findExistingApps(String name) {
        Specification<AppEntity> specification = Specification.where(null);
        Specification<AppEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 过滤非删除app
            predicates.add(criteriaBuilder.isNull(root.get(AppEntity_.DELETED_AT)));

            predicates.add(PredicateBuilder.startWithPredicate(criteriaBuilder, root.get(AppEntity_.NAME), name));

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        return this.appRepository.findAll(specification);
    }

    @Transactional
    public Commit commit(CommitCreateInput commitCreateInput, boolean skipPublish) {
        // CHECK IF APP and  EXISTS
        this.clear();
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        String appId = commitCreateInput.getApp().getConnect().getId();
        String userId = authUserDetail.getUserId();

        List<PendingChange> changedEntities = this.entityService.getChangedEntities(appId, userId);
        List<PendingChange> changedBlocks = this.blockService.getChangedBlocks(appId, userId);

        Commit commit = this.commitService.createCommit(commitCreateInput);

        changedEntities.forEach(pendingChange -> {
            this.entityVersionService.createVersion(pendingChange.getResourceId(), commit.getId());
            this.entityService.releaseLock(pendingChange.getResourceId());
        });

        changedBlocks.forEach(pendingChange -> {
            this.blockVersionService.createVersion(pendingChange.getResourceId(), commit.getId());
            this.blockService.releaseLock(pendingChange.getResourceId());
        });

        this.buildService.createBuild(appId, commit.getId(), userId, commitCreateInput.getMessage(), skipPublish);

        return commit;
    }

    @Transactional
    public App deleteApp(WhereUniqueInput where) {
        AppEntity appEntity = this.appRepository.getById(where.getId());
        appEntity.setName("__" + appEntity.getId() + "__" + appEntity.getName());
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
        String appId = where.getApp().getId();
        String userId = SecurityUtils.getAuthUser().getUserId();
        List<PendingChange> pendingChanges = this.entityService.getChangedEntities(appId, userId);

        pendingChanges.addAll(this.blockService.getChangedBlocks(appId, userId));

        return pendingChanges;
    }

    public AppValidationResult appValidateBeforeCommit(WhereUniqueInput where) {
        AppValidationResult validationResult = new AppValidationResult();
        validationResult.setIsValid(true);
        validationResult.setMessages(Collections.emptyList());
        return validationResult;
    }

    //创建domain
    private EntityFieldCreateByDisplayNameInput createFieldByDisplayName(Entity entity, String displayName,
                                                                         EnumDataType dataType, String userId) {
        EntityFieldCreateByDisplayNameInput.Builder builder = new EntityFieldCreateByDisplayNameInput.Builder();
        WhereParentIdInput.Builder entityBuilder = WhereParentIdInput.builder();
        entityBuilder.setConnect(WhereUniqueInput.builder().setId(entity.getId()).build());
        builder.setDisplayName(displayName).setDataType(dataType).setEntity(entityBuilder.build());
        return builder.build();
    }
}
