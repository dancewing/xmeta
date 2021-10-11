package io.xmeta.admin.service;

import io.xmeta.admin.domain.UserRoleEntity;
import io.xmeta.admin.repository.UserRoleRepository;
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
public class UserRoleService extends BaseService<UserRoleRepository, UserRoleEntity, String> {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        super(userRoleRepository);
        this.userRoleRepository = userRoleRepository;
    }
}
