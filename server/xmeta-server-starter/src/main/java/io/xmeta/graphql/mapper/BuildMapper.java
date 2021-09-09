package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.BuildEntity;
import io.xmeta.graphql.model.Build;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AppMapper.class, UserMapper.class, CommitMapper.class, AppMapper.class,
        ActionMapper.class})
public interface BuildMapper extends BaseMapper<Build, BuildEntity> {

    @Mapping(target = "userId", source = "createdBy.id")
    @Mapping(target = "appId", source = "app.id")
    @Mapping(target = "commitId", source = "commit.id")
    @Mapping(target = "actionId", source = "action.id")
    @Mapping(target = "status", ignore = true)
    Build toDto(BuildEntity entity);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "containerStatusQuery", ignore = true)
    @Mapping(target = "containerStatusUpdatedAt", ignore = true)
    @Mapping(target = "blockVersions", ignore = true)
    @Mapping(target = "entityVersions", ignore = true)
    BuildEntity toEntity(Build dto);
}
