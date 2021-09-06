package io.xmeta.graphql.service;
import java.time.ZonedDateTime;
import java.util.Arrays;

import io.xmeta.graphql.domain.AccountEntity;
import io.xmeta.graphql.domain.UserEntity;
import io.xmeta.graphql.domain.UserRoleEntity;
import io.xmeta.graphql.mapper.UserMapper;
import io.xmeta.graphql.mapper.WorkspaceMapper;
import io.xmeta.graphql.model.Role;
import io.xmeta.graphql.model.User;
import io.xmeta.graphql.model.Workspace;
import io.xmeta.graphql.model.WorkspaceCreateInput;
import io.xmeta.graphql.repository.UserRepository;
import io.xmeta.graphql.repository.UserRoleRepository;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.WorkspaceEntity;
import io.xmeta.graphql.repository.WorkspaceRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class WorkspaceService extends BaseService<WorkspaceRepository, WorkspaceEntity, String> {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper, UserMapper userMapper, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        super(workspaceRepository);
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public Workspace createWorkspace(WorkspaceCreateInput data) {
        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.setCreatedAt(ZonedDateTime.now());
        workspaceEntity.setUpdatedAt(ZonedDateTime.now());
        workspaceEntity.setName(data.getName());
        return this.workspaceMapper.toDto(this.workspaceRepository.save(workspaceEntity));
    }

    public Workspace currentWorkspace() {
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        WorkspaceEntity workspaceEntity = this.workspaceRepository.getById(authUserDetail.getWorkspaceId());
        Workspace workspace = new Workspace();
        workspace.setId(workspaceEntity.getId());
        workspace.setName(workspaceEntity.getName());
        return workspace;
    }

    @Transactional
    public Workspace createWorkspace(String accountId, String workspaceName) {
        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.setCreatedAt(ZonedDateTime.now());
        workspaceEntity.setUpdatedAt(ZonedDateTime.now());
        workspaceEntity.setName(workspaceName);
        this.workspaceRepository.save(workspaceEntity);

        Workspace workspace = this.workspaceMapper.toDto(workspaceEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setUpdatedAt(ZonedDateTime.now());
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        userEntity.setAccount(accountEntity);
        userEntity.setWorkspace(workspaceEntity);
        userEntity.setIsOwner(true);
        this.userRepository.save(userEntity);

        User user = this.userMapper.toDto(userEntity);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setCreatedAt(ZonedDateTime.now());
        userRoleEntity.setUpdatedAt(ZonedDateTime.now());
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(Role.OrganizationAdmin.name());

        this.userRoleRepository.save(userRoleEntity);
        workspace.setUsers(Arrays.asList(user));

        return workspace;

    }
}
