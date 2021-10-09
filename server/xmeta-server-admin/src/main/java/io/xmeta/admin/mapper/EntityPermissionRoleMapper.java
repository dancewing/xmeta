package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.EntityPermissionRoleEntity;
import io.xmeta.graphql.model.EntityPermissionRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AppMapper.class, AppRoleMapper.class, EntityPermissionMapper.class})
public interface EntityPermissionRoleMapper extends BaseMapper<EntityPermissionRole, EntityPermissionRoleEntity> {
    @Mapping(target = "permissionFields", ignore = true)
    EntityPermissionRoleEntity toEntity(EntityPermissionRole dto);

    @Mapping(target = "appRoleId", source = "appRole.id")
        //@Mapping(target = "entityPermission", source = "permission")
    EntityPermissionRole toDto(EntityPermissionRoleEntity entity);
}
