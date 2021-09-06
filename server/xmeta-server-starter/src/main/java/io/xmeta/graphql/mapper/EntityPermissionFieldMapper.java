package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityPermissionFieldEntity;
import io.xmeta.graphql.model.EntityPermissionField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityPermissionMapper.class,
        EntityFieldMapper.class,
        EntityPermissionRoleMapper.class})
public interface EntityPermissionFieldMapper extends BaseMapper<EntityPermissionField, EntityPermissionFieldEntity> {
    @Override
    EntityPermissionFieldEntity toEntity(EntityPermissionField dto);

    @Mapping(target = "permissionId", source = "permission.id")
    EntityPermissionField toDto(EntityPermissionFieldEntity entity);
}
