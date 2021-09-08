package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.BuildMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.*;
import io.xmeta.graphql.util.Maps;
import io.xmeta.graphql.util.ObjectMapperUtils;
import io.xmeta.graphql.util.PredicateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class BuildService extends BaseService<BuildRepository, BuildEntity, String> {

    private final BuildRepository buildRepository;
    private final BuildMapper buildMapper;
    private final EntityVersionRepository entityVersionRepository;
    private final EntityRepository entityRepository;
    private final ActionLogRepository actionLogRepository;
    private final ActionStepRepository actionStepRepository;
    private final ActionRepository actionRepository;

    public BuildService(BuildRepository buildRepository, BuildMapper buildMapper, EntityVersionRepository entityVersionRepository, EntityRepository entityRepository, ActionLogRepository actionLogRepository, ActionStepRepository actionStepRepository, ActionRepository actionRepository) {
        super(buildRepository);
        this.buildRepository = buildRepository;
        this.buildMapper = buildMapper;
        this.entityVersionRepository = entityVersionRepository;
        this.entityRepository = entityRepository;
        this.actionLogRepository = actionLogRepository;
        this.actionStepRepository = actionStepRepository;
        this.actionRepository = actionRepository;
    }

    public Build getBuild(String id) {
        return this.buildMapper.toDto(this.buildRepository.getById(id));
    }

    public List<Build> builds(App app, Commit commit, BuildWhereInput where, BuildOrderByInput orderBy, Integer take,
                              Integer skip) {
        Specification<BuildEntity> specification = Specification.where(null);
        Specification<BuildEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (app != null && app.getId() != null) {
                Join<Object, Object> join = root.join(BuildEntity_.APP, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(AppEntity_.ID), app.getId()));
            }
            if (commit != null && commit.getId() != null) {
                Join<Object, Object> join = root.join(BuildEntity_.COMMIT, JoinType.LEFT);
                predicates.add(PredicateBuilder.equalsPredicate(criteriaBuilder, join.get(CommitEntity_.ID), commit.getId()));
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<BuildEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.buildRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.buildRepository.findAll(specification, sort);
        }
        return result.stream().map(this.buildMapper::toDto).collect(Collectors.toList());
    }

    private void createInitialLogData(String version, String message, ActionStepEntity actionStepEntity) {
        ActionLogEntity actionLogEntity = new ActionLogEntity();
        actionLogEntity.setCreatedAt(ZonedDateTime.now());
        actionLogEntity.setMessage("create build generation task");
        actionLogEntity.setMeta(ObjectMapperUtils.toBytes(Maps.empty()));
        actionLogEntity.setLevel(EnumActionLogLevel.Info.name());
        actionLogEntity.setStep(actionStepEntity);
        this.actionLogRepository.save(actionLogEntity);

        actionLogEntity = new ActionLogEntity();
        actionLogEntity.setCreatedAt(ZonedDateTime.now());
        actionLogEntity.setMessage("Build Version: " + version);
        actionLogEntity.setMeta(ObjectMapperUtils.toBytes(Maps.empty()));
        actionLogEntity.setLevel(EnumActionLogLevel.Info.name());
        actionLogEntity.setStep(actionStepEntity);
        this.actionLogRepository.save(actionLogEntity);

        actionLogEntity = new ActionLogEntity();
        actionLogEntity.setCreatedAt(ZonedDateTime.now());
        actionLogEntity.setMessage("Build message: " + message);
        actionLogEntity.setMeta(ObjectMapperUtils.toBytes(Maps.empty()));
        actionLogEntity.setLevel(EnumActionLogLevel.Info.name());
        actionLogEntity.setStep(actionStepEntity);
        this.actionLogRepository.save(actionLogEntity);

    }

    private ActionStepEntity createInitialStepData(String version, String message, ActionEntity actionEntity) {
        ActionStepEntity actionStepEntity = new ActionStepEntity();
        actionStepEntity.setCreatedAt(ZonedDateTime.now());
        actionStepEntity.setMessage(message);
        actionStepEntity.setStatus(EnumActionStepStatus.Success.name());
        actionStepEntity.setCompletedAt(ZonedDateTime.now());
        actionStepEntity.setName("ADD_TO_QUEUE");
        actionStepEntity.setAction(actionEntity);
        this.actionStepRepository.save(actionStepEntity);

        this.createInitialLogData(version, message, actionStepEntity);

        return actionStepEntity;
    }

    @Transactional
    public void createBuild(String appId, String commitId, String userId, String message, boolean skipPublish) {

        // get latest version for each entities of one app
        List<EntityEntity> entityEntities = this.entityRepository.findEntitiesByApp(appId); //not deleted
        List<EntityVersionEntity> latestVersions = new ArrayList<>();
        for (EntityEntity entity : entityEntities) {
            latestVersions.addAll(this.entityVersionRepository.getLatestVersions(entity.getId(), Pageable.ofSize(1)));
        }
        String version = commitId.substring(commitId.length() - 8);
        BuildEntity buildEntity = new BuildEntity();
        buildEntity.setCreatedAt(ZonedDateTime.now());
        AppEntity appEntity = new AppEntity();
        appEntity.setId(appId);
        buildEntity.setApp(appEntity);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        buildEntity.setCreatedBy(userEntity);
        buildEntity.setVersion(version);
        buildEntity.setMessage(message);
        CommitEntity commitEntity = new CommitEntity();
        commitEntity.setId(commitId);
        buildEntity.setCommit(commitEntity);
        buildEntity.getEntityVersions().addAll(latestVersions);

        //create action
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setCreatedAt(ZonedDateTime.now());
        this.actionRepository.save(actionEntity);

        this.createInitialStepData(version, message, actionEntity);
        buildEntity.setAction(actionEntity);

        this.buildRepository.saveAndFlush(buildEntity);

        //TODO
    }
}
