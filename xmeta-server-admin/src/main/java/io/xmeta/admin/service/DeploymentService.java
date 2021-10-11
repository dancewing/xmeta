package io.xmeta.admin.service;

import io.xmeta.admin.domain.BuildEntity;
import io.xmeta.admin.domain.DeploymentEntity;
import io.xmeta.admin.domain.EnvironmentEntity;
import io.xmeta.admin.mapper.DeploymentMapper;
import io.xmeta.admin.model.Deployment;
import io.xmeta.admin.model.DeploymentCreateInput;
import io.xmeta.admin.model.EnumDeploymentStatus;
import io.xmeta.admin.repository.DeploymentRepository;
import io.xmeta.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

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

    public DeploymentService(DeploymentRepository deploymentRepository, DeploymentMapper deploymentMapper) {
        super(deploymentRepository);
        this.deploymentRepository = deploymentRepository;
        this.deploymentMapper = deploymentMapper;
    }

    @Transactional
    public Deployment createDeployment(DeploymentCreateInput data) {

        String userId = SecurityUtils.getAuthUser().getUserId();
        DeploymentEntity deploymentEntity = new DeploymentEntity();

        BuildEntity build = new BuildEntity();
        build.setId(data.getBuildId());

        EnvironmentEntity environment = new EnvironmentEntity();
        environment.setId(data.getEnvironmentId());

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
}
