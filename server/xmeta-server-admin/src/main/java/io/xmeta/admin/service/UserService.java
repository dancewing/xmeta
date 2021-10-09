package io.xmeta.admin.service;

import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.repository.UserRepository;
import io.xmeta.admin.mapper.UserMapper;
import io.xmeta.graphql.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional
@PreAuthorize("isAuthenticated()")
public class UserService extends BaseService<UserRepository, UserEntity, String> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUser(String userId) {
        return this.userMapper.toDto(this.userRepository.getById(userId));
    }

    public List<User> findUsers(String workspaceId, String accountId) {
        return this.userMapper.toDto(this.userRepository.findUsers(accountId, workspaceId));
    }

}
