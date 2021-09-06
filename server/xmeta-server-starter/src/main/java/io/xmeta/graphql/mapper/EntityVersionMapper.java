package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityVersionEntity;
import io.xmeta.graphql.model.EntityVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityPermissionMapper.class, CommitMapper.class, EntityMapper.class})
public interface EntityVersionMapper extends BaseMapper<EntityVersion, EntityVersionEntity> {

    EntityVersion toDto(EntityVersionEntity entity);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "builds", ignore = true)
    @Mapping(target = "fields", ignore = true)
    EntityVersionEntity toEntity(EntityVersion dto);
}
