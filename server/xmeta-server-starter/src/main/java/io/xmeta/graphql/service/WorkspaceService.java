package io.xmeta.graphql.service;

import io.xmeta.graphql.model.Workspace;
import io.xmeta.security.AuthUser;
import io.xmeta.security.SecurityUtils;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.WorkspaceEntity;
import io.xmeta.graphql.repository.WorkspaceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class WorkspaceService extends BaseService<WorkspaceRepository, WorkspaceEntity, String> {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        super(workspaceRepository);
        this.workspaceRepository = workspaceRepository;
    }

    public Workspace currentWorkspace() {
        AuthUser authUser = SecurityUtils.getAuthUser();
        WorkspaceEntity workspaceEntity = this.workspaceRepository.getById(authUser.getWorkspaceId());
        Workspace workspace = new Workspace();
        workspace.setId(workspaceEntity.getId());
        workspace.setName(workspaceEntity.getName());
        return workspace;
    }
}
