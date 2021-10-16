package io.xmeta.core.sqlbuilder;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public interface SqlParameterSourceCreator {
    SqlParameterSource createSqlParameterSource();
}
