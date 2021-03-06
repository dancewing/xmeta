package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.UserRoleEntity;
import io.xmeta.admin.model.Role;
import io.xmeta.admin.model.UserRole;
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
        if (role != null)
            return role.name();
        return "";
    }
}
