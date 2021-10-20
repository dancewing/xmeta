package io.xmeta.deploy;

import io.xmeta.deploy.impl.DeployServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeployConfiguration {

    @Bean
    public DeployService deployService() {
        return new DeployServiceImpl();
    }
}
