package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.EntityVersionEntity;
import io.xmeta.graphql.model.EntityVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityPermissionMapper.class, CommitMapper.class, EntityMapper.class})
public interface EntityVersionMapper extends BaseMapper<EntityVersion, EntityVersionEntity> {

    @Mapping(target = "permissions", ignore = true)
    EntityVersion toDto(EntityVersionEntity entity);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "builds", ignore = true)
        // @Mapping(target = "fields", ignore = true)
    EntityVersionEntity toEntity(EntityVersion dto);
}
