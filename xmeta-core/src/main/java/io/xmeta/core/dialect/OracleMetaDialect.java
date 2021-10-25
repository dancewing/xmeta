package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.OracleDialect;

public class OracleMetaDialect extends AbstractDialectWrapper<OracleDialect> {

    public OracleMetaDialect(OracleDialect dialect) {
        super(dialect);
    }
}
