package io.xmeta.web.config;

import io.xmeta.core.service.MetaCacheLoaderService;
import io.xmeta.core.service.MetaDatabaseLoaderService;
import io.xmeta.core.service.MetaRefreshService;
import io.xmeta.core.service.impl.MetaRefreshServiceImpl;
import io.xmeta.web.handler.ActionInterceptor;
import io.xmeta.web.handler.EmptyActionInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.xmeta.web.controller", "io.xmeta.web.handler",
        "io.xmeta.web.registrar", "io.xmeta.web.service", "io.xmeta.web.swagger"})
public class MetaWebConfiguration {
    @Bean
    @ConditionalOnMissingBean(ActionInterceptor.class)
    public ActionInterceptor actionInterceptor() {
        return new EmptyActionInterceptor();
    }

    @Bean
    public MetaRefreshService metaRefreshService(MetaDatabaseLoaderService metaLoaderService, MetaCacheLoaderService metaCacheLoaderService) {
        return new MetaRefreshServiceImpl(metaLoaderService, metaCacheLoaderService);
    }

}
