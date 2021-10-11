package io.xmeta.admin.service;

import io.xmeta.admin.domain.AppEntity;
import io.xmeta.admin.domain.EnvironmentEntity;
import io.xmeta.admin.mapper.EnvironmentMapper;
import io.xmeta.admin.model.Environment;
import io.xmeta.admin.model.EnvironmentCreateInput;
import io.xmeta.admin.model.EnvironmentUpdateInput;
import io.xmeta.admin.model.WhereUniqueInput;
import io.xmeta.admin.repository.EnvironmentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

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
    private final EnvironmentMapper environmentMapper;

    public EnvironmentService(EnvironmentRepository environmentRepository, EnvironmentMapper environmentMapper) {
        super(environmentRepository);
        this.environmentRepository = environmentRepository;
        this.environmentMapper = environmentMapper;
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

    public List<Environment> environments(WhereUniqueInput where) {
        return this.environmentMapper.toDto(this.environmentRepository.getAllByApp(where.getId()));
    }

    public Environment environment(WhereUniqueInput where) {
        return this.environmentMapper.toDto(this.environmentRepository.getById(where.getId()));
    }

    @Transactional
    public Environment createEnvironment(EnvironmentCreateInput data) {
        AppEntity appEntity = new AppEntity();
        appEntity.setId(data.getAppId());
        EnvironmentEntity environment = new EnvironmentEntity();
        environment.setCreatedAt(ZonedDateTime.now());
        environment.setUpdatedAt(ZonedDateTime.now());
        environment.setName(data.getName());
        environment.setDescription(data.getDescription());
        environment.setAddress(data.getAddress());
        environment.setUser(data.getUser());
        environment.setPassword(data.getPassword());
        environment.setApp(appEntity);
        return this.environmentMapper.toDto(this.environmentRepository.save(environment));
    }

    @Transactional
    public Environment updateEnvironment(EnvironmentUpdateInput data, WhereUniqueInput where) {
        AppEntity appEntity = new AppEntity();
        appEntity.setId(data.getAppId());
        EnvironmentEntity environment = this.environmentRepository.getById(where.getId());
        environment.setUpdatedAt(ZonedDateTime.now());
        environment.setName(data.getName());
        environment.setDescription(data.getDescription());
        environment.setAddress(data.getAddress());
        environment.setUser(data.getUser());
        environment.setPassword(data.getPassword());
        environment.setApp(appEntity);
        return this.environmentMapper.toDto(this.environmentRepository.save(environment));
    }

    @Transactional
    public Environment deleteEnvironment(WhereUniqueInput where) {
        EnvironmentEntity environment = this.environmentRepository.getById(where.getId());
        this.environmentRepository.delete(environment);
        return this.environmentMapper.toDto(environment);
    }
}
