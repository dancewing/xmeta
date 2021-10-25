package io.xmeta.deploy;

import io.xmeta.core.dialect.MetaDialect;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.deploy.impl.DeployServiceImpl;
import io.xmeta.screw.SchemaLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeployConfiguration {

    @Bean
    public DeployService deployService(SchemaLoader schemaLoader, MetaDialect metaDialect, MetaEntityService metaEntityService) {
        return new DeployServiceImpl(schemaLoader, metaDialect, metaEntityService);
    }
}
