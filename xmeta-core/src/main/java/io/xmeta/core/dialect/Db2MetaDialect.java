package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.Db2Dialect;


public class Db2MetaDialect extends AbstractDialectWrapper<Db2Dialect> {
    public Db2MetaDialect(Db2Dialect db2Dialect) {
        super(db2Dialect);
    }
}
