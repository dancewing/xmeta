package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityEntity;
import io.xmeta.graphql.model.Entity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AppMapper.class})
public interface EntityMapper extends BaseMapper<Entity, EntityEntity> {

    @Mapping(source = "app.id", target = "appId")
    @Mapping(source = "lockedByUser.id", target = "lockedByUserId")
    Entity toDto(EntityEntity entity);

    EntityEntity toEntity(Entity dto);
}
