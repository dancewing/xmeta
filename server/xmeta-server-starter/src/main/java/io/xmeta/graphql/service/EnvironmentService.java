package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.EnvironmentEntity;
import io.xmeta.graphql.repository.EnvironmentRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class EnvironmentService extends BaseService<EnvironmentRepository, EnvironmentEntity, String> {

    private final EnvironmentRepository environmentRepository;

    public EnvironmentService(EnvironmentRepository environmentRepository) {
        super(environmentRepository);
        this.environmentRepository = environmentRepository;
    }
}
