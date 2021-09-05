package io.xmeta.security.auth;

import io.xmeta.security.handler.PermissionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 自定义PermissionEvaluator，用于支持spring security中SecurityExpressionRoot的hasPermission 方法
 *
 * @see SecurityExpressionRoot#hasPermission(Object, Object)
 * @see SecurityExpressionRoot#hasPermission(Object, String, Object)
 */
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {

	private PermissionHandler permissionHandler;

	@Autowired
	public void setPermissionHandler(PermissionHandler permissionHandler) {
		this.permissionHandler = permissionHandler;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
		Object targetDomainObject, Object permission) {

		if (authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
//			if (principal instanceof WishbuildUserDetails) {
//				WishbuildUserDetails userDetails = (WishbuildUserDetails) principal;
//				return permissionHandler.hasPermission(userDetails.getUserId(), (String)permission);
//			}
		}
		log.info("permission check {}", permission);
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
		Serializable targetId, String targetType, Object permission) {
		log.info("permission check {}, {} , {}", targetId, targetType, permission);
		return hasPermission(authentication, null,  permission);
	}
}
