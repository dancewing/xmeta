package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.SqlServerDialect;

public class SqlServerMetaDialect extends AbstractDialectWrapper<SqlServerDialect> {

    public SqlServerMetaDialect(SqlServerDialect dialect) {
        super(dialect);
    }
}
