package io.xmeta.core.config;

import io.xmeta.core.dao.DefaultEntityJdbcTemplate;
import io.xmeta.core.dao.EntityJdbcTemplate;
import io.xmeta.core.dao.MetaJdbcProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Configuration
@EnableConfigurationProperties(MetaJdbcProperties.class)
@Slf4j
public class MetaConfiguration {

    @Primary
    @Bean
    public EntityJdbcTemplate entityDataAccessStrategy(JdbcConverter converter, NamedParameterJdbcOperations operations,
                                                       Dialect dialect) {
        return new DefaultEntityJdbcTemplate(converter, operations, dialect);
    }
}
