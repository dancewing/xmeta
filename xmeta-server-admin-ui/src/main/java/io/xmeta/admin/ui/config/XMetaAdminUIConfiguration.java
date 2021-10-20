package io.xmeta.admin.ui.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.web.servlet.view.UrlBasedViewResolver.FORWARD_URL_PREFIX;

@Configuration
@Slf4j
public class XMetaAdminUIConfiguration implements WebMvcConfigurer {

    public static final String ADMIN_UI_PATH = "/xmeta-admin-ui";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index.html");
    }

    @Controller
    public static class AdminUIHome {
        @GetMapping(ADMIN_UI_PATH)
        public String index() {
            log.info("Request home page");
            return FORWARD_URL_PREFIX + ADMIN_UI_PATH + "/index.html";
        }
    }
}
