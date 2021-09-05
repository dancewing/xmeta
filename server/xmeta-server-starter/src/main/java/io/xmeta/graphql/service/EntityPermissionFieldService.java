package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.EntityPermissionFieldEntity;
import io.xmeta.graphql.repository.EntityPermissionFieldRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class EntityPermissionFieldService extends BaseService<EntityPermissionFieldRepository, EntityPermissionFieldEntity, String> {

    private final EntityPermissionFieldRepository permissionfieldRepository;

    public EntityPermissionFieldService(EntityPermissionFieldRepository permissionfieldRepository) {
        super(permissionfieldRepository);
        this.permissionfieldRepository = permissionfieldRepository;
    }
}
