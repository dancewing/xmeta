package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.DeploymentEntity;
import io.xmeta.graphql.repository.DeploymentRepository;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
public class DeploymentService extends BaseService<DeploymentRepository, DeploymentEntity, String> {

    private final DeploymentRepository deploymentRepository;

    public DeploymentService(DeploymentRepository deploymentRepository) {
        super(deploymentRepository);
        this.deploymentRepository = deploymentRepository;
    }
}
