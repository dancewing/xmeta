package io.xmeta.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"io.xmeta.admin.controller", "io.xmeta.admin.mapper",
        "io.xmeta.admin.resolver", "io.xmeta.admin.service"})
@Configuration
public class MetaAdminConfiguration {
}
