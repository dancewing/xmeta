package io.xmeta.core.config;

import io.xmeta.core.service.MetaLoaderService;
import io.xmeta.core.service.impl.JdbcMetaLoaderService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetaConfiguration {

    @Bean
    @ConditionalOnMissingBean(JdbcMetaLoaderService.class)
    public MetaLoaderService metaLoaderService() {
        return new JdbcMetaLoaderService();
    }
}
