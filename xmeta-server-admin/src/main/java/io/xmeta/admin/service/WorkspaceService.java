package io.xmeta.admin.service;

import io.xmeta.admin.domain.AccountEntity;
import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.domain.UserRoleEntity;
import io.xmeta.admin.domain.WorkspaceEntity;
import io.xmeta.admin.mapper.UserMapper;
import io.xmeta.admin.mapper.WorkspaceMapper;
import io.xmeta.admin.mix.WorkspaceDomain;
import io.xmeta.admin.model.*;
import io.xmeta.admin.repository.UserRepository;
import io.xmeta.admin.repository.UserRoleRepository;
import io.xmeta.admin.repository.WorkspaceRepository;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author Jeff
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
    private final AppService appService;

    public WorkspaceService(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper, UserMapper userMapper, UserRepository userRepository, UserRoleRepository userRoleRepository, AppService appService) {
        super(workspaceRepository);
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.appService = appService;
    }

    @Transactional
    public Workspace createWorkspace(WorkspaceCreateInput data) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();

        return this.createWorkspace(authUser.getAccountId(), data.getName());
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

    public List<Workspace> getCurrentAccountWorkspaces() {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        if (authUser != null) {
            List<WorkspaceEntity> workspaces = this.userRepository.findWorkspaceByAccount(authUser.getAccountId());
            return this.workspaceMapper.toDto(workspaces);
        }
        return Collections.emptyList();
    }

    public Workspace getWorkspace(String id) {
        return this.workspaceMapper.toDto(this.workspaceRepository.getById(id));
    }

    @Transactional
    public Workspace updateWorkspace(WorkspaceUpdateInput data, WhereUniqueInput where) {
        WorkspaceEntity workspaceEntity = this.workspaceRepository.getById(where.getId());
        workspaceEntity.setUpdatedAt(ZonedDateTime.now());
        workspaceEntity.setName(data.getName());
        return this.workspaceMapper.toDto(this.workspaceRepository.save(workspaceEntity));
    }

    /***
     * 获取元数据库信息
     * @return
     */
    public List<WorkspaceDomain> metadata() {
        List<Workspace> workspaces = this.getCurrentAccountWorkspaces();
        List<WorkspaceDomain> workspaceDomains = new ArrayList<>();
        for (Workspace workspace : workspaces) {
            WorkspaceDomain workspaceDomain = new WorkspaceDomain();
            workspaceDomain.setId(workspace.getId());
            workspaceDomain.setName(workspace.getName());
            workspaceDomain.setApps(this.appService.loadApps(workspace.getId()));
            workspaceDomains.add(workspaceDomain);
        }
        return workspaceDomains;
    }
}
