package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityVersionEntity;
import io.xmeta.graphql.model.EntityVersion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EntityVersionMapper extends BaseMapper<EntityVersion, EntityVersionEntity> {
    EntityVersion toDto(EntityVersionEntity entity);

    EntityVersionEntity toEntity(EntityVersion dto);
}
