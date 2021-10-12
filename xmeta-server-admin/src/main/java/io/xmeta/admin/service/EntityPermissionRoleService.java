package io.xmeta.admin.service;

import io.xmeta.admin.domain.EntityPermissionRoleEntity;
import io.xmeta.admin.repository.EntityPermissionRoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@PreAuthorize("isAuthenticated()")
public class EntityPermissionRoleService extends BaseService<EntityPermissionRoleRepository, EntityPermissionRoleEntity, String> {

    private final EntityPermissionRoleRepository permissionRoleRepository;

    public EntityPermissionRoleService(EntityPermissionRoleRepository permissionRoleRepository) {
        super(permissionRoleRepository);
        this.permissionRoleRepository = permissionRoleRepository;
    }
}
