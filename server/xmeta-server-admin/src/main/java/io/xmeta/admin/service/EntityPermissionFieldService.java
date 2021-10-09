package io.xmeta.admin.service;

import io.xmeta.admin.domain.EntityPermissionFieldEntity;
import io.xmeta.admin.repository.EntityPermissionFieldRepository;
import io.xmeta.graphql.model.EntityAddPermissionFieldInput;
import io.xmeta.graphql.model.EntityPermissionField;
import io.xmeta.graphql.model.EntityPermissionFieldWhereUniqueInput;
import io.xmeta.graphql.model.EntityUpdatePermissionFieldRolesInput;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@PreAuthorize("isAuthenticated()")
@Transactional(readOnly = true)
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
