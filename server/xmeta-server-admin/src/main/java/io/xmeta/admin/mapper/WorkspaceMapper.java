package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.WorkspaceEntity;
import io.xmeta.admin.model.Workspace;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AppMapper.class})
public interface WorkspaceMapper extends BaseMapper<Workspace, WorkspaceEntity> {
    Workspace toDto(WorkspaceEntity entity);

    WorkspaceEntity toEntity(Workspace dto);
}
