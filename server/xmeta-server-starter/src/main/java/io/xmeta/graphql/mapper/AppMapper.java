package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.model.App;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface AppMapper extends BaseMapper<App, AppEntity> {
    App toDto(AppEntity entity);

    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "workspace", ignore = true)
    @Mapping(target = "githubToken", ignore = true)
    AppEntity toEntity(App dto);
}
