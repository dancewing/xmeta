package io.xmeta.admin.service;

import com.zaxxer.hikari.HikariDataSource;
import io.xmeta.admin.model.Environment;
import io.xmeta.admin.model.WhereUniqueInput;
import io.xmeta.core.exception.MetaException;
import io.xmeta.core.utils.jdbc.DatabaseInfo;
import io.xmeta.core.utils.jdbc.JdbcUrlParserFactory;
import io.xmeta.core.utils.jdbc.UnKnownDatabaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.schemaspy.Config;
import org.schemaspy.SchemaLoader;
import org.schemaspy.model.Database;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            config.setDataSource(dataSource);
            config.setDbType(databaseType);

            return this.schemaLoader.analyze(config, schema);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    private static String getDatabase(String jdbcUrl) {
        Pattern pattern = Pattern.compile("(?<=databaseName=)\\w+(?=;|$)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static String getDataSourceType(String jdbcUrl) {
        String dataSourceType = null;
        jdbcUrl = jdbcUrl.replaceAll("\n", "").replaceAll(" ", "").trim();
        Matcher matcher = Pattern.compile("jdbc:\\w+").matcher(jdbcUrl);
        if (matcher.find()) {
            dataSourceType = matcher.group().split(":")[1];
        }
        return dataSourceType;
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
