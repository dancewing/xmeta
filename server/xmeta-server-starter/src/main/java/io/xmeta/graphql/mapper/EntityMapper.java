package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityEntity;
import io.xmeta.graphql.model.Entity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EntityMapper extends BaseMapper<Entity, EntityEntity> {
    Entity toDto(EntityEntity entity);
}
