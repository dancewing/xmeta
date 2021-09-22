package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.EntityPermissionRoleEntity;
import io.xmeta.graphql.repository.EntityPermissionRoleRepository;
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

    private final EntityPermissionRoleRepository permissionroleRepository;

    public EntityPermissionRoleService(EntityPermissionRoleRepository permissionroleRepository) {
        super(permissionroleRepository);
        this.permissionroleRepository = permissionroleRepository;
    }
}
