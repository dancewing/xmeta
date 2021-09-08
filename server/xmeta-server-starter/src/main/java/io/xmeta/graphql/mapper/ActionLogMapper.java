package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.ActionLogEntity;
import io.xmeta.graphql.model.ActionLog;
import io.xmeta.graphql.util.ObjectMapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", uses = {})
public interface ActionLogMapper extends BaseMapper<ActionLog, ActionLogEntity> {
    @Mapping(target = "step", ignore = true)
    ActionLogEntity toEntity(ActionLog dto);

    ActionLog toDto(ActionLogEntity entity);

    default byte[] toBytes(Map<String, Object> value) {
        return ObjectMapperUtils.toBytes(value);
    }

    default Map<String, Object> toMap(byte[] bytes) {
        return ObjectMapperUtils.toMap(bytes);
    }
}
