package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.ActionEntity;
import io.xmeta.graphql.model.Action;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ActionStepMapper.class})
public interface ActionMapper extends BaseMapper<Action, ActionEntity> {
    @Override
    ActionEntity toEntity(Action dto);

    @Override
    Action toDto(ActionEntity entity);
}
