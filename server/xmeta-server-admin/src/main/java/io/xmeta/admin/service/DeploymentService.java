package io.xmeta.admin.service;

import io.xmeta.admin.domain.DeploymentEntity;
import io.xmeta.admin.repository.DeploymentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public DeploymentService(DeploymentRepository deploymentRepository) {
        super(deploymentRepository);
        this.deploymentRepository = deploymentRepository;
    }
}
