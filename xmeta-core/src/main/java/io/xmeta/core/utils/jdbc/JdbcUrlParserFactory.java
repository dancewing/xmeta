package io.xmeta.core.utils.jdbc;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdbcUrlParserFactory {
    public static String getDatabaseType(String jdbcUrl) {
        String dataSourceType = null;
        jdbcUrl = jdbcUrl.replaceAll("\n", "").replaceAll(" ", "").trim();
        Matcher matcher = Pattern.compile("jdbc:\\w+").matcher(jdbcUrl);
        if (matcher.find()) {
            dataSourceType = matcher.group().split(":")[1];
        }
        return dataSourceType;
    }


    public static DatabaseInfo getDatabaseInfo(String jdbcUrl) {
        String databaseType = StringUtils.lowerCase(getDatabaseType(jdbcUrl));
        JdbcUrlParser parser = null;
        switch (databaseType) {
            case "mysql":
                parser = MySqlJdbcUrlParser.INSTANCE;
                break;
            case "oracle":
                parser = OracleJdbcUrlParser.INSTANCE;
                break;
            case "postgres":
                parser = PostgreSqlJdbcUrlParser.INSTANCE;
                break;
            case "sqlserver":
                parser = MssqlJdbcUrlParser.INSTANCE;
                break;
        }
        if (parser == null) {
            return UnKnownDatabaseInfo.INSTANCE;
        } else {
            return parser.parse(jdbcUrl);
        }
    }
}
