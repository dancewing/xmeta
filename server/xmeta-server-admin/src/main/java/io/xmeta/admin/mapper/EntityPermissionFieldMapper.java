package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.EntityPermissionFieldEntity;
import io.xmeta.admin.model.EntityPermissionField;
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
