package io.xmeta.security.auth;

import io.xmeta.security.handler.PermissionHandler;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final PermissionHandler permissionHandler;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, PermissionHandler permissionHandler) {
        super(authentication);
        this.permissionHandler = permissionHandler;
    }

    public boolean hasPermission(Object permission) {
        if (permission instanceof String) {
            return this.permissionHandler.hasPermission(this.authentication, (String) permission);
        }
        return false;
    }

    private Object filterObject;

    private Object returnObject;

    private Object target;

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    /**
     * Sets the "this" property for use in expressions. Typically this will be the "this"
     * property of the {@code JoinPoint} representing the method invocation which is being
     * protected.
     *
     * @param target the target object on which the method in is being invoked.
     */
    void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return this.target;
    }
}
