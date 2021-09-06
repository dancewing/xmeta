package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.WorkspaceEntity;
import io.xmeta.graphql.model.Workspace;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface WorkspaceMapper extends BaseMapper<Workspace, WorkspaceEntity> {
    Workspace toDto(WorkspaceEntity entity);

    WorkspaceEntity toEntity(Workspace dto);
}
