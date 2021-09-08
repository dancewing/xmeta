package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.EntityFieldEntity;
import io.xmeta.graphql.model.EntityField;
import io.xmeta.graphql.util.ObjectMapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EntityFieldMapper extends BaseMapper<EntityField, EntityFieldEntity> {

    @Mapping(target = "entityVersion", ignore = true)
    EntityFieldEntity toEntity(EntityField dto);

    EntityField toDto(EntityFieldEntity entity);

    default byte[] toBytes(Map<String, Object> value) {
        return ObjectMapperUtils.toBytes(value);
    }

    default Map<String, Object> toMap(byte[] bytes) {
        return ObjectMapperUtils.toMap(bytes);
    }
}
