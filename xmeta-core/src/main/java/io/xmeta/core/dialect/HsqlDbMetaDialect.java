package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.HsqlDbDialect;

public class HsqlDbMetaDialect extends AbstractDialectWrapper<HsqlDbDialect> {

    public HsqlDbMetaDialect(HsqlDbDialect dialect) {
        super(dialect);
    }

    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }
}
