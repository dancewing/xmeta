package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.ActionLogEntity;
import io.xmeta.admin.model.ActionLog;
import io.xmeta.core.utils.ObjectMapperUtils;
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
