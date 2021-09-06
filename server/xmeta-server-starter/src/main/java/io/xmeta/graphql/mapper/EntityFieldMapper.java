package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityFieldEntity;
import io.xmeta.graphql.model.EntityField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EntityFieldMapper extends BaseMapper<EntityField, EntityFieldEntity> {
    EntityFieldEntity toEntity(EntityField dto);

    EntityField toDto(EntityFieldEntity entity);
}
