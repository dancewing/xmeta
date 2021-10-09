package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.ActionEntity;
import io.xmeta.admin.model.Action;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ActionStepMapper.class})
public interface ActionMapper extends BaseMapper<Action, ActionEntity> {
    @Override
    ActionEntity toEntity(Action dto);

    @Mapping(target = "steps", ignore = true)
    Action toDto(ActionEntity entity);
}
