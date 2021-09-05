package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.model.App;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AppMapper extends BaseMapper<App, AppEntity> {
    App toDto(AppEntity entity);
}
