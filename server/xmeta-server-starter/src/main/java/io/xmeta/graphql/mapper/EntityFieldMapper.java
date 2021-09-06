package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityFieldEntity;
import io.xmeta.graphql.model.EntityField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EntityFieldMapper extends BaseMapper<EntityField, EntityFieldEntity> {

    @Mapping(target = "entityVersion", ignore = true)
    EntityFieldEntity toEntity(EntityField dto);

    EntityField toDto(EntityFieldEntity entity);
}
