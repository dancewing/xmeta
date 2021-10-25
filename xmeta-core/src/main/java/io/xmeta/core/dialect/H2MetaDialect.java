package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.H2Dialect;

public class H2MetaDialect extends AbstractDialectWrapper<H2Dialect> {
    public H2MetaDialect(H2Dialect dialect) {
        super(dialect);
    }

    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }
}
