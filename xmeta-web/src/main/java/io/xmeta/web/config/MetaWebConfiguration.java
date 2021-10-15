package io.xmeta.web.config;

import io.xmeta.core.service.MetaLoaderService;
import io.xmeta.core.service.MetaRefreshService;
import io.xmeta.core.service.impl.JdbcMetaLoaderService;
import io.xmeta.core.service.impl.MetaRefreshServiceImpl;
import io.xmeta.web.handler.ActionInterceptor;
import io.xmeta.web.handler.internal.EmptyActionInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MetaWebConfiguration {
    @Bean
    @ConditionalOnMissingBean(ActionInterceptor.class)
    public ActionInterceptor actionInterceptor() {
        return new EmptyActionInterceptor();
    }


    @Bean
    @ConditionalOnMissingBean(JdbcMetaLoaderService.class)
    public MetaLoaderService metaLoaderService(DataSource dataSource) {
        return new JdbcMetaLoaderService(dataSource);
    }

    @Bean
    public MetaRefreshService metaRefreshService(MetaLoaderService metaLoaderService) {
        return new MetaRefreshServiceImpl(metaLoaderService);
    }
}
