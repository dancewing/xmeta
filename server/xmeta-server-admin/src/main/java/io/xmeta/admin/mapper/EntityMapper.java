package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.EntityEntity;
import io.xmeta.graphql.model.Entity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AppMapper.class, EntityPermissionMapper.class, UserMapper.class})
public interface EntityMapper extends BaseMapper<Entity, EntityEntity> {

    @Mapping(source = "app.id", target = "appId")
    @Mapping(source = "lockedByUser.id", target = "lockedByUserId")
    @Mapping(target = "permissions", ignore = true)
    Entity toDto(EntityEntity entity);

    @Mapping(target = "deletedAt", ignore = true)
        //@Mapping(target = "versions", ignore = true)
    EntityEntity toEntity(Entity dto);
}
