package io.xmeta.core.utils.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class MySqlJdbcUrlParser implements JdbcUrlParser {

    public static final MySqlJdbcUrlParser INSTANCE = new MySqlJdbcUrlParser();

    static final String URL_PREFIX = "jdbc:mysql:";
    // jdbc:mysql:loadbalance://10.22.33.44:3306,10.22.33.55:3306/MySQL?characterEncoding=UTF-8
    private static final String LOADBALANCE_URL_PREFIX = URL_PREFIX + "loadbalance:";

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
            log.info("MySqlJdbcUrl parse error. url: {}, Caused: {}", jdbcUrl, e.getMessage(), e);
            result = UnKnownDatabaseInfo.INSTANCE;
        }
        return result;
    }

    private DatabaseInfo parse0(String jdbcUrl) {
        // jdbc:mysql://1.2.3.4:5678/test_db
        StringMaker maker = new StringMaker(jdbcUrl);
        maker.after(URL_PREFIX);
        // 1.2.3.4:5678 In case of replication driver could have multiple values
        // We have to consider mm db too.
        String host = maker.after("//").before('/').value();
        List<String> hostList = parseHost(host);

        String databaseId = maker.next().after('/').before('?').value();
        String normalizedUrl = maker.clear().before('?').value();

        return new DefaultDatabaseInfo(jdbcUrl, normalizedUrl, hostList, databaseId);
    }

    private List<String> parseHost(String host) {
        final int multiHost = host.indexOf(",");
        if (multiHost == -1) {
            return Collections.singletonList(host);
        }
        // Decided not to cache regex. This is not invoked often so don't waste memory.
        String[] parsedHost = host.split(",");
        return Arrays.asList(parsedHost);
    }
}
