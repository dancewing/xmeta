package io.xmeta.graphql.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.xmeta.graphql.domain")
@EnableJpaRepositories(basePackages = {"io.xmeta.graphql.repository"})
public class XMetaGraphQLConfiguration {
    @Bean
    public GraphQLScalarType datetime() {
        return ExtendedScalars.DateTime;
    }

    @Bean
    public GraphQLScalarType json() {
        return ExtendedScalars.Json;
    }
}
