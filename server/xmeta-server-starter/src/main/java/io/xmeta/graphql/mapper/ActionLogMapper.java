package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.ActionLogEntity;
import io.xmeta.graphql.model.ActionLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ActionLogMapper extends BaseMapper<ActionLog, ActionLogEntity> {
    @Mapping(target = "step", ignore = true)
    ActionLogEntity toEntity(ActionLog dto);

    ActionLog toDto(ActionLogEntity entity);
}
