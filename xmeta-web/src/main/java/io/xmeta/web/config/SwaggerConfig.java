package io.xmeta.web.config;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;


@Configuration
@EnableSwagger2
public class SwaggerConfig  implements WebMvcConfigurer {

    @Autowired
    private  SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(swaggerProperties.isEnable())
                .pathMapping("/")
                .select()
                // 选择哪些接口作为swagger的doc发布
                //.apis(RequestHandlerSelectors.basePackage("io.xmeta.web.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
                //.enable(swaggerProperties.getEnable())

                // 将api的元信息设置为包含在json ResourceListing响应中。


                 //接口调试地址
                //.host(swaggerProperties.getTryHost())



                // 支持的通讯协议集合
                //.protocols(newHashSet("https", "http"))

                // 授权信息设置，必要的header token等认证信息
                //.securitySchemes(securitySchemes());

                // 授权信息全局应用
                //.securityContexts(securityContexts());
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerProperties.getApplicationName() + " Api Doc")
                .description(swaggerProperties.getApplicationDescription())
                .contact(new Contact("lighter", null, "123456@gmail.com"))
                .version("Application Version: " + swaggerProperties.getApplicationVersion() + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("BASE_TOKEN", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("BASE_TOKEN", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }
}
