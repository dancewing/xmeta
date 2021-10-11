package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.WorkspaceEntity;
import io.xmeta.admin.model.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper extends BaseMapper<Workspace, WorkspaceEntity> {
    @Override
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "apps", ignore = true)
    Workspace toDto(WorkspaceEntity entity);

    @Override
    WorkspaceEntity toEntity(Workspace dto);
}
