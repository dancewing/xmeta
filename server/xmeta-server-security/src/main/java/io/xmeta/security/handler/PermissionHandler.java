package io.xmeta.security.handler;

import org.springframework.security.core.Authentication;
import io.xmeta.security.Permission;

import java.util.List;

/**
 * 权限校验通用接口
 *
 * @author wishbuild
 */
public interface PermissionHandler {

	String SCOPE_CACHE_CODE = "permission:code:";

	String SYS_CACHE = "wishbuild:sys";

	/**
	 * 判断角色是否具有接口权限
	 *
	 * @param userId 用户ID
	 * @param permission 权限编号
	 * @return {boolean}
	 */
	boolean hasPermission(String userId, String permission);

	boolean hasPermission(Authentication authentication, String permission);

	void cachePermissions(String userId, List<? extends Permission> permissions);

	void clearPermissions(String userId);

	List<Permission> getPermissions(String userId);
}
