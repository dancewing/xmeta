package io.xmeta.web.config;

import io.xmeta.web.handler.ActionInterceptor;
import io.xmeta.web.handler.internal.EmptyActionInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XMetaConfiguration {
    @Bean
    @ConditionalOnMissingBean(ActionInterceptor.class)
    public ActionInterceptor actionInterceptor() {
        return new EmptyActionInterceptor();
    }
}
