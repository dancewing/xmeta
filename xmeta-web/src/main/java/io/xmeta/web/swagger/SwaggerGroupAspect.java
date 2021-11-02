package io.xmeta.web.swagger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;

import java.util.Map;
import java.util.Set;

/**
 * 强制给 /v3/api-docs/swagger-config 接口塞入一个分组值
 */
@Aspect
@Slf4j
public class SwaggerGroupAspect {

    @AfterReturning(value = "execution(* org.springdoc.webmvc.ui.SwaggerConfigResource.openapiJson(..))", returning = "result")
    public void changeReturnResource(JoinPoint joinPoint, Object result) {
        if (result instanceof Map) {
            Map<String, Object> configs = (Map<String, Object>) result;
            Set urls = (Set) configs.get("urls");
            if (urls != null) {
                urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Meta API", "/v3/api/meta-docs"));
            }
        }
    }

}
