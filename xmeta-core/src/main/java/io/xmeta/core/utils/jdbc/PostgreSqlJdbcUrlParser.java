package io.xmeta.core.utils.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PostgreSqlJdbcUrlParser implements JdbcUrlParser {

    public static final PostgreSqlJdbcUrlParser INSTANCE = new PostgreSqlJdbcUrlParser();

    private static final String URL_PREFIX = "jdbc:postgresql:";

    @Override
    public DatabaseInfo parse(String jdbcUrl) {
        if (jdbcUrl == null) {
            log.info("jdbcUrl must not be null");
            return UnKnownDatabaseInfo.INSTANCE;
        }
        if (!jdbcUrl.startsWith(URL_PREFIX)) {
            log.info("jdbcUrl has invalid prefix.(url:{}, prefix:{})", jdbcUrl, URL_PREFIX);
            return UnKnownDatabaseInfo.INSTANCE;
        }

        DatabaseInfo result = null;
        try {
            result = parse0(jdbcUrl);
        } catch (Exception e) {
            log.info("PostgreJdbcUrl parse error. url: {}, Caused: {}", jdbcUrl, e.getMessage(), e);
            result = UnKnownDatabaseInfo.INSTANCE;
        }
        return result;
    }

    private DatabaseInfo parse0(String url) {
        // jdbc:postgresql://1.2.3.4:5678/test_db
        StringMaker maker = new StringMaker(url);
        maker.after(URL_PREFIX);
        String hosts = maker.after("//").before('/').value();
        List<String> hostList = Arrays.asList(StringUtils.split(hosts, ","));
        String databaseId = maker.next().afterLast('/').before('?').value();
        String normalizedUrl = maker.clear().before('?').value();
        return new DefaultDatabaseInfo(url, normalizedUrl, hostList, databaseId);
    }
}
