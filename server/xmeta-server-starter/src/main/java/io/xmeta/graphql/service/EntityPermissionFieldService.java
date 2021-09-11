package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.EntityPermissionFieldEntity;
import io.xmeta.graphql.model.EntityAddPermissionFieldInput;
import io.xmeta.graphql.model.EntityPermissionField;
import io.xmeta.graphql.model.EntityPermissionFieldWhereUniqueInput;
import io.xmeta.graphql.model.EntityUpdatePermissionFieldRolesInput;
import io.xmeta.graphql.repository.EntityPermissionFieldRepository;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
public class EntityPermissionFieldService extends BaseService<EntityPermissionFieldRepository, EntityPermissionFieldEntity, String> {

    private final EntityPermissionFieldRepository permissionfieldRepository;

    public EntityPermissionFieldService(EntityPermissionFieldRepository permissionfieldRepository) {
        super(permissionfieldRepository);
        this.permissionfieldRepository = permissionfieldRepository;
    }

    public EntityPermissionField addEntityPermissionField(EntityAddPermissionFieldInput data) {
        return null;
    }

    public EntityPermissionField deleteEntityPermissionField(EntityPermissionFieldWhereUniqueInput where) {
        return null;
    }

    public EntityPermissionField updateEntityPermissionFieldRoles(EntityUpdatePermissionFieldRolesInput data) {
        return null;
    }
}
