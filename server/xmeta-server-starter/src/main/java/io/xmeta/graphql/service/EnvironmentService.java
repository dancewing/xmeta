package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.domain.EnvironmentEntity;
import io.xmeta.graphql.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class EnvironmentService extends BaseService<EnvironmentRepository, EnvironmentEntity, String> {

    private final EnvironmentRepository environmentRepository;

    public EnvironmentService(EnvironmentRepository environmentRepository) {
        super(environmentRepository);
        this.environmentRepository = environmentRepository;
    }

    @Transactional
    public void createDefaultEnvironment(String appId) {
        EnvironmentEntity environment = new EnvironmentEntity();
        AppEntity appEntity = new AppEntity();
        appEntity.setId(appId);
        environment.setApp(appEntity);
        environment.setName("Sandbox environment");
        this.environmentRepository.save(environment);
    }
}
