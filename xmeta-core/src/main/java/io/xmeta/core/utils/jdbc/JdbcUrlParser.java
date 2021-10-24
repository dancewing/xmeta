package io.xmeta.core.utils.jdbc;

public interface JdbcUrlParser {

    DatabaseInfo parse(String url);

}
