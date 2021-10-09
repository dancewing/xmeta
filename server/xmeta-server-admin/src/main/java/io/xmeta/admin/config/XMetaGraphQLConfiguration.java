package io.xmeta.admin.config;

import graphql.kickstart.tools.TypeDefinitionFactory;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import io.xmeta.admin.mix.CustomTypeDefinitionFactory;
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
@EntityScan("io.xmeta.admin.domain")
@EnableJpaRepositories(basePackages = {"io.xmeta.admin.repository"})
public class XMetaGraphQLConfiguration {
    @Bean
    public GraphQLScalarType datetime() {
        return ExtendedScalars.DateTime;
    }

    @Bean
    public GraphQLScalarType json() {
        return GraphQLScalarType.newScalar().name("JSONObject").description("A JSON scalar").coercing(ExtendedScalars.Json.getCoercing()).build();
    }

    @Bean
    public ImplicitNamingStrategy implicitNamingStrategy() {
        return new SpringImplicitNamingStrategy();
    }

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new SpringPhysicalNamingStrategy();
    }

    @Bean
    public TypeDefinitionFactory metaDefinitionFactory() {
        return new CustomTypeDefinitionFactory();
    }
}
