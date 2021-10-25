package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.PostgresDialect;

public class PostgresMetaDialect extends AbstractDialectWrapper<PostgresDialect> {

    public PostgresMetaDialect(PostgresDialect dialect) {
        super(dialect);
    }

    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    public boolean supportsIfExistsAfterAlterTable() {
        return true;
    }
}
