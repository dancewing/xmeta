package io.xmeta.graphql.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
