package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.ActionEntity;
import io.xmeta.graphql.model.Action;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ActionStepMapper.class})
public interface ActionMapper extends BaseMapper<Action, ActionEntity> {
    @Override
    ActionEntity toEntity(Action dto);

    @Mapping(target = "steps", ignore = true)
    Action toDto(ActionEntity entity);
}
