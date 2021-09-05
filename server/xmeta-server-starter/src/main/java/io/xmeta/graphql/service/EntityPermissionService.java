package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.EntityPermissionEntity;
import io.xmeta.graphql.repository.EntityPermissionRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class EntityPermissionService extends BaseService<EntityPermissionRepository, EntityPermissionEntity, String> {

    private final EntityPermissionRepository permissionRepository;

    public EntityPermissionService(EntityPermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
    }
}
