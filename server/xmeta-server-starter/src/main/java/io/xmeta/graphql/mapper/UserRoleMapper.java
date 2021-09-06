package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.UserRoleEntity;
import io.xmeta.graphql.model.Role;
import io.xmeta.graphql.model.UserRole;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends BaseMapper<UserRole, UserRoleEntity> {

    UserRole toDto(UserRoleEntity entity);

    @Mapping(target = "user", ignore = true)
    UserRoleEntity toEntity(UserRole dto);

    default Role toRole(String role) {
        if (StringUtils.isEmpty(role)) return null;
        return Role.valueOf(role);
    }

    default String toRole(Role role) {
        if (role!=null)
            return role.name();
        return "";
    }
}
