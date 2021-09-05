package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.DeploymentEntity;
import io.xmeta.graphql.repository.DeploymentRepository;

/**
 * @Description  
 * @Author  Jeff
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
