package io.xmeta.core.config;

import io.xmeta.core.dao.DefaultEntityJdbcTemplate;
import io.xmeta.core.dao.EntityJdbcTemplate;
import io.xmeta.core.dao.MetaJdbcProperties;
import io.xmeta.core.dialect.MetaDialect;
import io.xmeta.core.dialect.MetaDialectResolver;
import io.xmeta.core.service.*;
import io.xmeta.core.service.impl.JdbcMetaLoaderService;
import io.xmeta.core.service.impl.MetaDataServiceImpl;
import io.xmeta.core.service.impl.MetaEntityServiceImpl;
import io.xmeta.core.service.impl.RedisMetaEntityCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(MetaJdbcProperties.class)
@Slf4j
public class MetaConfiguration {

    @Primary
    @Bean
    public MetaDialect metaDialect(Dialect dialect) {
        return MetaDialectResolver.getDialect(dialect);
    }

    @Primary
    @Bean
    public EntityJdbcTemplate entityDataAccessStrategy(JdbcConverter converter, NamedParameterJdbcOperations operations,
                                                       Dialect dialect) {
        return new DefaultEntityJdbcTemplate(converter, operations, dialect);
    }

    @Primary
    @Bean
    public MetaDataService metaDataService(MetaEntityService metaEntityService, EntityJdbcTemplate entityJdbcTemplate, FieldConversionService fieldConversionService) {
        return new MetaDataServiceImpl(metaEntityService, entityJdbcTemplate, fieldConversionService);
    }

    @Bean
    @ConditionalOnMissingBean(MetaDatabaseLoaderService.class)
    public MetaDatabaseLoaderService metaLoaderService(DataSource dataSource) {
        return new JdbcMetaLoaderService(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(MetaCacheLoaderService.class)
    public MetaCacheLoaderService metaCacheLoaderService() {
        return new RedisMetaEntityCacheService();
    }

    @Bean
    @ConditionalOnMissingBean(MetaEntityService.class)
    public MetaEntityService metaEntityService() {
        return new MetaEntityServiceImpl();
    }


    @Bean
    @ConditionalOnMissingBean(FieldConversionService.class)
    public FieldConversionService fieldConversionService(ConversionService conversionService, DataTypeMapping dataTypeMapping) {
        return new FieldConversionService(conversionService, dataTypeMapping);
    }

    @Bean
    @ConditionalOnMissingBean(DataTypeMapping.class)
    public DataTypeMapping dataTypeMapping() {
        return new DataTypeMapping();
    }

}
