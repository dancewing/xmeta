package io.xmeta.graphql.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
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

//    @Bean
//    public Validator validator() {
//        return Validation.buildDefaultValidatorFactory().getValidator();
//    }

    @Bean
    public ImplicitNamingStrategy implicitNamingStrategy() {
        return new SpringImplicitNamingStrategy();
    }

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new SpringPhysicalNamingStrategy();
    }

}
