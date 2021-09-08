package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.UserRoleEntity;
import io.xmeta.graphql.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
public class UserRoleService extends BaseService<UserRoleRepository, UserRoleEntity, String> {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        super(userRoleRepository);
        this.userRoleRepository = userRoleRepository;
    }
}
