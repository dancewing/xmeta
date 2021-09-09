package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.UserEntity;
import io.xmeta.graphql.mapper.UserMapper;
import io.xmeta.graphql.model.User;
import io.xmeta.graphql.repository.UserRepository;
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
