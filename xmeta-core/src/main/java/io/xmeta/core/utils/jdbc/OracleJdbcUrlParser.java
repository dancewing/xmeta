package io.xmeta.core.utils.jdbc;

import io.xmeta.core.utils.jdbc.oracle.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OracleJdbcUrlParser implements JdbcUrlParser {

    public static final OracleJdbcUrlParser INSTANCE = new OracleJdbcUrlParser();

    private static final String URL_PREFIX = "jdbc:oracle:";

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
            return parse0(jdbcUrl);
        } catch (Exception e) {
            log.info("OracleJdbcUrl parse error. url: {}, Caused: {}", jdbcUrl, e.getMessage(), e);
            result = UnKnownDatabaseInfo.INSTANCE;
        }
        return result;
    }

    private DatabaseInfo parse0(String jdbcUrl) {
        StringMaker maker = new StringMaker(jdbcUrl);
        maker.after(URL_PREFIX).after(":");
        String description = maker.after('@').value().trim();
        if (description.startsWith("(")) {
            return parseNetConnectionUrl(jdbcUrl);
        } else {
            return parseSimpleUrl(jdbcUrl, maker);
        }
    }

    private DatabaseInfo parseNetConnectionUrl(String url) {
        // oracle new URL : for rac
        OracleNetConnectionDescriptorParser parser = new OracleNetConnectionDescriptorParser(url);
        KeyValue<?> keyValue = parser.parse();
        // TODO Need to handle oci driver. It's more popular.
//                parser.getDriverType();
        return createOracleDatabaseInfo(keyValue, url);
    }

    private DefaultDatabaseInfo parseSimpleUrl(String url, StringMaker maker) {
        // thin driver
        // jdbc:oracle:thin:@hostname:port:SID
        // "jdbc:oracle:thin:MYWORKSPACE/qwerty@localhost:1521:XE";
//      jdbc:oracle:thin:@//hostname:port/serviceName
        String host = maker.before(':').value();
        String port = maker.next().after(':').before(':', '/').value();
        String databaseId = maker.next().afterLast(':', '/').value();

        List<String> hostList = new ArrayList<>(1);
        hostList.add(host + ":" + port);
        return new DefaultDatabaseInfo(url, url, hostList, databaseId);
    }

    private DatabaseInfo createOracleDatabaseInfo(KeyValue<?> keyValue, String url) {
        final DatabaseSpec databaseSpec = newDatabaseSpec(keyValue);
        if (databaseSpec == null) {
            return UnKnownDatabaseInfo.INSTANCE;
        }

        String databaseId = databaseSpec.getDatabaseId();
        List<String> jdbcHost = databaseSpec.getJdbcHost();
        return new DefaultDatabaseInfo(url, url, jdbcHost, databaseId);
    }

    private DatabaseSpec newDatabaseSpec(KeyValue<?> keyValue) {
        if (ParserUtils.compare(Description.DESCRIPTION, keyValue)) {
            return new Description(keyValue);
        } else if (ParserUtils.compare(DescriptionList.DESCRIPTION_LIST, keyValue)) {
            return new DescriptionList(keyValue);
        }
        return null;
    }
}
