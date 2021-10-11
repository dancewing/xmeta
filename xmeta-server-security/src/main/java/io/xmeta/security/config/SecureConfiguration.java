package io.xmeta.security.config;


import io.xmeta.security.handler.PermissionHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import io.xmeta.security.auth.CustomMethodSecurityExpressionHandler;
import io.xmeta.security.auth.CustomPermissionEvaluator;
import io.xmeta.security.auth.SpringSecurityAuditorAware;
import io.xmeta.security.handler.PermissionHandlerImpl;

/**
 * 安全配置类
 *
 * @author wishbuild
 */
@Order
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class SecureConfiguration extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler(permissionHandler());
        expressionHandler.setPermissionEvaluator(permissionEvaluator());
        return expressionHandler;
    }

    @Bean
    @ConditionalOnMissingBean(PermissionHandler.class)
    public PermissionHandler permissionHandler() {
        return new PermissionHandlerImpl();
    }

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomPermissionEvaluator();
    }

    @Bean
    public SpringSecurityAuditorAware springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }
}
