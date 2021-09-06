package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.UserRoleEntity;
import io.xmeta.graphql.model.Role;
import io.xmeta.graphql.model.UserRole;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends BaseMapper<UserRole, UserRoleEntity> {

    UserRole toDto(UserRoleEntity entity);

    UserRoleEntity toEntity(UserRole dto);

    default Role toRole(String role) {
        if (StringUtils.isEmpty(role)) return null;
        return Role.valueOf(role);
    }
}
