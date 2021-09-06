package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityPermissionEntity;
import io.xmeta.graphql.model.EntityPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityPermissionRoleMapper.class, EntityPermissionFieldMapper.class,
        EntityVersionMapper.class})
public interface EntityPermissionMapper extends BaseMapper<EntityPermission, EntityPermissionEntity> {
    @Override
    EntityPermissionEntity toEntity(EntityPermission dto);

    @Mapping(target = "entityVersionId", source = "entityVersion.id")
    EntityPermission toDto(EntityPermissionEntity entity);
}
