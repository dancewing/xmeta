package io.xmeta.security.handler;

import io.xmeta.security.SecurityUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import io.xmeta.security.Permission;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认授权校验类
 */
public class PermissionHandlerImpl implements PermissionHandler {

	@Resource
	RedisTemplate<String, Permission> redisTemplate;

	@Override
	public boolean hasPermission(String userId, String permission) {
		List<Permission> authorities = getPermissions(userId);
		for (Permission p : authorities) {
			if (StringUtils.equals(permission, p.getAction())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, String permission) {
		if (authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof User) {
				User user = (User) principal;
				return this.hasPermission(user.getUsername(), permission);
			}
		}
		return false;
	}

	@Override
	public void cachePermissions(String userId,
		List<? extends Permission> permissions) {
		if (StringUtils.isEmpty(userId) || ObjectUtils.isEmpty(permissions)) return;
		String key = SYS_CACHE+ ":" + SCOPE_CACHE_CODE + ":" + userId;
		List<Permission> permissionsList = permissions.stream().map(permission -> SecurityUtils.toPermission(permission.getAction(), permission.getSubject(), permission.getFields(), permission.getConditions())).collect(Collectors.toList());
		this.redisTemplate.opsForList().rightPushAll(key, permissionsList);
	}

	@Override
	public void clearPermissions(String userId) {
		if (StringUtils.isEmpty(userId)) return;
		String key = SYS_CACHE+ ":" + SCOPE_CACHE_CODE + ":" + userId;
		this.redisTemplate.delete(key);
	}

	/**
	 * 获取接口权限地址
	 *
	 * @param userId 用户id
	 * @return permissions
	 */
	public List<Permission> getPermissions(String userId) {
		String key = SYS_CACHE+ ":" + SCOPE_CACHE_CODE + ":" + userId;
		List<Permission> permissions = this.redisTemplate.opsForList().range(key, 0 , -1);
		if (permissions == null) return Collections.emptyList();
		return new ArrayList<>(permissions);
	}

}
