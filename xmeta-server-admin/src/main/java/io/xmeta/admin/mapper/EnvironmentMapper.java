package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.EnvironmentEntity;
import io.xmeta.admin.model.Environment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnvironmentMapper extends BaseMapper<Environment, EnvironmentEntity> {
    @Override
    @Mapping(source = "appId", target = "app.id")
    EnvironmentEntity toEntity(Environment dto);

    @Override
    @Mapping(source = "app.id", target = "appId")
    Environment toDto(EnvironmentEntity entity);
}
