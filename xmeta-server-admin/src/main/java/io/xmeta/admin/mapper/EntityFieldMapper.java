package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.EntityFieldEntity;
import io.xmeta.core.utils.ObjectMapperUtils;
import io.xmeta.admin.model.EntityField;
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
