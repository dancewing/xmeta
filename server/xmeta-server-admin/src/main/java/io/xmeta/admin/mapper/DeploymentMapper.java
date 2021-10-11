package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.DeploymentEntity;
import io.xmeta.admin.model.Deployment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EnvironmentMapper.class, BuildMapper.class, ActionMapper.class})
public interface DeploymentMapper extends BaseMapper<Deployment, DeploymentEntity> {
    @Override
    DeploymentEntity toEntity(Deployment dto);

    @Override
    Deployment toDto(DeploymentEntity entity);
}
