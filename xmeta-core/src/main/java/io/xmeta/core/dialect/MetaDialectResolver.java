package io.xmeta.core.dialect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.dialect.*;
import org.springframework.util.Assert;

@Slf4j
public class MetaDialectResolver {

    public static MetaDialect getDialect(Dialect dialect){

        Assert.notNull(dialect, "Dialect can't be null");

        if (dialect instanceof HsqlDbDialect) {
          return new HsqlDbMetaDialect((HsqlDbDialect) dialect);
        }

        if (dialect instanceof H2Dialect) {
            return new H2MetaDialect((H2Dialect) dialect);
        }

        if (dialect instanceof MySqlDialect) {
            return new MySqlMetaDialect((MySqlDialect) dialect);
        }

        if (dialect instanceof PostgresDialect) {
            return new PostgresMetaDialect((PostgresDialect) dialect);
        }

        if (dialect instanceof SqlServerDialect) {
            return new SqlServerMetaDialect((SqlServerDialect) dialect);
        }

        if (dialect instanceof Db2Dialect) {
            return new Db2MetaDialect((Db2Dialect) dialect);
        }

        if (dialect instanceof OracleDialect) {
            return new OracleMetaDialect((OracleDialect) dialect);
        }

        log.info(String.format("Couldn't determine Meta Dialect for \"%s\"", dialect.getClass().getName()) );
        return null;
    }
}
