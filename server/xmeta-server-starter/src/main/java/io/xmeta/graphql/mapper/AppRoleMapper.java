package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.AppRoleEntity;
import io.xmeta.graphql.model.AppRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface AppRoleMapper extends BaseMapper<AppRole, AppRoleEntity> {
    @Mapping(target = "app", ignore = true)
    AppRoleEntity toEntity(AppRole dto);

    @Override
    AppRole toDto(AppRoleEntity entity);
}
