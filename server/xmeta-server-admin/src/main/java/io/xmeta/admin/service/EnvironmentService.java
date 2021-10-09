package io.xmeta.admin.service;

import io.xmeta.admin.domain.AppEntity;
import io.xmeta.admin.domain.EnvironmentEntity;
import io.xmeta.admin.repository.EnvironmentRepository;
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
@Transactional(readOnly = true)
@PreAuthorize("isAuthenticated()")
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
        environment.setCreatedAt(ZonedDateTime.now());
        environment.setCreatedAt(ZonedDateTime.now());
        environment.setDescription("Sandbox");
        environment.setName("Sandbox environment");
        this.environmentRepository.save(environment);
    }
}
