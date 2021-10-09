package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.ActionStepEntity;
import io.xmeta.graphql.model.ActionStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ActionLogMapper.class})
public interface ActionStepMapper extends BaseMapper<ActionStep, ActionStepEntity> {
    @Mapping(target = "action", ignore = true)
    ActionStepEntity toEntity(ActionStep dto);

    @Mapping(target = "logs", ignore = true)
    ActionStep toDto(ActionStepEntity entity);
}
