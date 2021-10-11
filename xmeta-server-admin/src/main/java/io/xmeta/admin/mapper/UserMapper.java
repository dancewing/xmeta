package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, WorkspaceMapper.class, UserRoleMapper.class})
public interface UserMapper extends BaseMapper<User, UserEntity> {
    User toDto(UserEntity entity);

    UserEntity toEntity(User dto);
}
