package io.xmeta.admin.service;

import com.zaxxer.hikari.HikariDataSource;
import io.xmeta.admin.model.Environment;
import io.xmeta.admin.model.WhereUniqueInput;
import io.xmeta.core.exception.MetaException;
import io.xmeta.core.utils.jdbc.DatabaseInfo;
import io.xmeta.core.utils.jdbc.JdbcUrlParserFactory;
import io.xmeta.core.utils.jdbc.UnKnownDatabaseInfo;
import io.xmeta.screw.Config;
import io.xmeta.screw.SchemaLoader;
import io.xmeta.screw.model.Database;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
@Slf4j
public class DatabaseMetaService {
    private final SchemaLoader schemaLoader;
    private final EnvironmentService environmentService;

    public DatabaseMetaService(SchemaLoader schemaLoader, EnvironmentService environmentService) {
        this.schemaLoader = schemaLoader;
        this.environmentService = environmentService;
    }

    public Database load(String environmentId) {
        Environment environment = this.environmentService.environment(WhereUniqueInput.builder().setId(environmentId).build());

        try (HikariDataSource dataSource = createDataSource(environment)) {
            try (Connection connection = dataSource.getConnection()) {
                String schema = dataSource.getSchema();
                Config config = new Config();
                String databaseType = JdbcUrlParserFactory.getDatabaseType(environment.getAddress());
                DatabaseInfo databaseInfo = JdbcUrlParserFactory.getDatabaseInfo(environment.getAddress());
                if (databaseInfo.equals(UnKnownDatabaseInfo.INSTANCE)) {
                    throw new MetaException("Unknown database");
                }
                if ("mysql".equals(databaseType)) {
                    if (StringUtils.isEmpty(schema)) {
                        schema = databaseInfo.getDatabaseId();
                    }
                }
                config.setDb(databaseInfo.getDatabaseId());
                config.setSchema(schema);
                config.setConnection(connection);
                config.setDbType(databaseType);

                return this.schemaLoader.analyze(config, schema);
            } catch (Exception e) {
                throw new MetaException(e.getMessage());
            }


        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    private HikariDataSource createDataSource(Environment environment) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(environment.getAddress());
        dataSource.setUsername(environment.getUser());
        dataSource.setPassword(environment.getPassword());
        dataSource.setMaximumPoolSize(1);
        dataSource.setMinimumIdle(1);
        return dataSource;
    }
}
