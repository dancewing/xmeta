package io.xmeta.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.web.swagger.MetaSwaggerApiService;
import io.xmeta.web.swagger.SwaggerGroupAspect;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ConditionalOnClass({OpenAPI.class})
@Slf4j
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Bean
    public MetaSwaggerApiService metaSwaggerApiService(MetaEntityService metaEntityService){
        return new MetaSwaggerApiService(metaEntityService);
    }

    @Bean
    public SwaggerGroupAspect swaggerGroupAspect() {
        return new SwaggerGroupAspect();
    }

    @Bean
    public GroupedOpenApi testOpenApi() {
        return GroupedOpenApi.builder()
                .group("Default")
                .packagesToScan("io.xmeta.web.controller")
                .build();
    }
}
