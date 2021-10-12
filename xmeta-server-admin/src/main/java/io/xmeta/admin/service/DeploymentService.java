package io.xmeta.admin.service;

import com.zaxxer.hikari.HikariDataSource;
import io.xmeta.admin.domain.BuildEntity;
import io.xmeta.admin.domain.DeploymentEntity;
import io.xmeta.admin.domain.EnvironmentEntity;
import io.xmeta.admin.mapper.DeploymentMapper;
import io.xmeta.admin.model.Deployment;
import io.xmeta.admin.model.DeploymentCreateInput;
import io.xmeta.admin.model.EnumDeploymentStatus;
import io.xmeta.admin.repository.BuildRepository;
import io.xmeta.admin.repository.DeploymentRepository;
import io.xmeta.admin.repository.EnvironmentRepository;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.DeployService;
import io.xmeta.security.SecurityUtils;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@PreAuthorize("isAuthenticated()")
@Transactional(readOnly = true)
public class DeploymentService extends BaseService<DeploymentRepository, DeploymentEntity, String> {

    private final DeploymentRepository deploymentRepository;
    private final DeploymentMapper deploymentMapper;
    private final DeployService deployService;
    private final EntityService entityService;
    private final BuildRepository buildRepository;
    private final EnvironmentRepository environmentRepository;

    public DeploymentService(DeploymentRepository deploymentRepository, DeploymentMapper deploymentMapper, DeployService deployService, EntityService entityService, BuildRepository buildRepository, EnvironmentRepository environmentRepository) {
        super(deploymentRepository);
        this.deploymentRepository = deploymentRepository;
        this.deploymentMapper = deploymentMapper;
        this.deployService = deployService;
        this.entityService = entityService;
        this.buildRepository = buildRepository;
        this.environmentRepository = environmentRepository;
    }

    @Transactional
    public Deployment createDeployment(DeploymentCreateInput data) {



        String userId = SecurityUtils.getAuthUser().getUserId();
        DeploymentEntity deploymentEntity = new DeploymentEntity();

        BuildEntity build = this.buildRepository.getById(data.getBuildId());
        List<Entity> entities = this.entityService.loadEntities(build.getApp().getId());

        EnvironmentEntity environment = this.environmentRepository.getById(data.getEnvironmentId());

        Map<String, Object> settings = new HashMap<>();

        this.deployService.deploy(entities, createDataSource(environment), settings, true, false);

        deploymentEntity.setCreatedAt(ZonedDateTime.now());
        deploymentEntity.setUserId(userId);
        deploymentEntity.setBuild(build);
        deploymentEntity.setEnvironment(environment);
        deploymentEntity.setStatus(EnumDeploymentStatus.Completed.name());
        deploymentEntity.setMessage(data.getMessage());
//        deploymentEntity.setActionId();
//        deploymentEntity.setAction();
        deploymentEntity.setStatusQuery("");
        deploymentEntity.setStatusUpdatedAt(ZonedDateTime.now());



        return this.deploymentMapper.toDto(this.deploymentRepository.save(deploymentEntity));
    }

    private DataSource createDataSource(EnvironmentEntity environment) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(environment.getAddress());
        dataSource.setUsername(environment.getUser());
        dataSource.setPassword(environment.getPassword());
        dataSource.setMaximumPoolSize(1);
        dataSource.setMinimumIdle(1);
        return dataSource;
    }
}
