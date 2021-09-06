package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.BuildEntity;
import io.xmeta.graphql.model.Build;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface BuildMapper extends BaseMapper<Build, BuildEntity> {
    Build toDto(BuildEntity entity);

    BuildEntity toEntity(Build dto);
}
