package io.xmeta.core.config;

import io.xmeta.core.MetaException;
import io.xmeta.core.sqlbuilder.dialect.SQLDialectCode;
import io.xmeta.core.sqlbuilder.MetaRuntime;
import io.xmeta.core.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Configuration
@Slf4j
public class MetaConfiguration {

    @Primary
    @Bean
    public MetaRuntime ormConfig(DataSource dataSource){
        return discoverConnectionProperties(dataSource);
    }

    private MetaRuntime discoverConnectionProperties(DataSource dataSource) {
        MetaRuntime metaRuntime = new MetaRuntime(dataSource);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                log.error("Could not get a connection");
                throw new MetaException("Could not get a connection");
            }
            DatabaseMetaData meta = connection.getMetaData();
            boolean supportsGetGeneratedKeys = DBUtils.supportsGetGeneratedKeys(meta);
            boolean supportsBatchUpdates = DBUtils.supportsBatchUpdates(meta);
            SQLDialectCode dialectCode = DBUtils.discoverSQLDialect(meta);
            log.info("Driver name=" + meta.getDriverName());
            log.info("Driver version=" + meta.getDriverVersion());
            log.info("SupportsGetGeneratedKeys=" + supportsGetGeneratedKeys);
            log.info("supportsBatchUpdates=" + supportsBatchUpdates);

            metaRuntime.setSupportsBatchUpdates(supportsBatchUpdates)
                    .setSupportsGetGeneratedKeys(supportsGetGeneratedKeys)
                    .setDialect(DBUtils.getDialectFromCode(dialectCode));
        } catch (SQLException se) {
            log.error("Could not discover the dialect to use.", se);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    // static utility classes should not log without an explicit repository
                    // reference
                }
            }
        }
        return metaRuntime;
    }

}
