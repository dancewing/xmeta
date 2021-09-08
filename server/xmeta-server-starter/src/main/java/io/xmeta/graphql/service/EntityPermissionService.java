package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.EntityPermissionEntity;
import io.xmeta.graphql.domain.EntityVersionEntity;
import io.xmeta.graphql.repository.EntityPermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class EntityPermissionService extends BaseService<EntityPermissionRepository, EntityPermissionEntity, String> {

    private final EntityPermissionRepository permissionRepository;

    public EntityPermissionService(EntityPermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public void createEntityPermission(String action, String type, EntityVersionEntity entityVersionEntity) {
        EntityPermissionEntity entityPermission = new EntityPermissionEntity();
        entityPermission.setEntityVersion(entityVersionEntity);
        entityPermission.setAction(action);
        entityPermission.setType(type);
        this.permissionRepository.save(entityPermission);
    }
}
