package io.xmeta.core.utils;

import io.xmeta.core.sqlbuilder.Dialect;
import io.xmeta.core.sqlbuilder.dialect.MySQLDialect;
import io.xmeta.core.sqlbuilder.dialect.PostgreSQLDialect;
import io.xmeta.core.sqlbuilder.dialect.SQLDialectCode;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Slf4j
public final class DBUtils {
    private static final String POSTGRES_PART = "postgresql";
    private static final String MYSQL_PART = "mysql";
    private static final String ORACLE_PART = "oracle";
    // private static final String MSSQL_PART = "mssqlserver4";
    private static final String MSSQL_PART = "microsoft";
    private static final String HSQL_PART = "hsql";
    private static final String H2_PART = "h2";
    private static final String SYBASE_SQLANY_PART = "sql anywhere";
    private static final String SQLITE_PART = "sqlite";

    public static SQLDialectCode discoverSQLDialect(DatabaseMetaData meta) {
        SQLDialectCode dialectCode = SQLDialectCode.UNKNOWN_DIALECT;

        try {

            String dbName = meta.getDatabaseProductName().toLowerCase();

            if (dbName.contains(POSTGRES_PART)) {
                return SQLDialectCode.POSTGRES_DIALECT;
            } else if (dbName.contains(MYSQL_PART)) {
                return SQLDialectCode.MYSQL_DIALECT;
            } else if (dbName.contains(ORACLE_PART)) {
                return SQLDialectCode.ORACLE_DIALECT;
            } else if (dbName.contains(MSSQL_PART)) {
                return SQLDialectCode.MSSQL_DIALECT;
            } else if (dbName.contains(HSQL_PART)) {
                return SQLDialectCode.HSQL_DIALECT;
            } else if (dbName.contains(H2_PART)) {
                return SQLDialectCode.H2_DIALECT;
            } else if (dbName.contains(SYBASE_SQLANY_PART)) {
                return SQLDialectCode.SYBASE_SQLANYWHERE_DIALECT;
            } else if (dbName.contains(SQLITE_PART)) {
                return SQLDialectCode.SQLITE_DIALECT;
            } else {
                return SQLDialectCode.UNKNOWN_DIALECT;
            }
        } catch (SQLException sqle) {
            // we can't do much here
        }

        return dialectCode;
    }

    public static Dialect getDialectFromCode(SQLDialectCode sqlDialectType) {
        Dialect sqlDialect = null;

        switch (sqlDialectType) {
            case POSTGRES_DIALECT:
                sqlDialect = new PostgreSQLDialect();
                break;

            case MYSQL_DIALECT:
                sqlDialect = new MySQLDialect();
                break;

//            case ORACLE_DIALECT:
//                sqlDialect = new OracleDialect();
//                break;
//
//            case MSSQL_DIALECT:
//                sqlDialect = new MsSQLDialect();
//                break;
//
//            case HSQL_DIALECT:
//                sqlDialect = new HSQLDBDialect();
//                break;
//
//            case H2_DIALECT:
//                sqlDialect = new H2Dialect();
//                break;
//
//            case SYBASE_SQLANYWHERE_DIALECT:
//                sqlDialect = new SybaseSqlAnywhereDialect();
//                break;
//
//            case SQLITE_DIALECT:
//                sqlDialect = new SQLiteDialect();
//                break;
        }
        return sqlDialect;
    }

    /**
     * This method handles cases where the
     * {@link DatabaseMetaData#supportsGetGeneratedKeys} method is missing in the
     * JDBC driver implementation.
     */
    public static boolean supportsGetGeneratedKeys(DatabaseMetaData meta) {
        try {
            //
            // invoking JDBC 1.4 method by reflection
            //
            return ((Boolean) DatabaseMetaData.class.getMethod("supportsGetGeneratedKeys", (Class[]) null).invoke(meta, (Object[]) null)).booleanValue();
        } catch (Throwable e) {
            log.error("Could not call supportsGetGeneratedKeys method. This may be recoverable");
            return false;
        }
    }

    /**
     * This method handles cases where the
     * {@link DatabaseMetaData#supportsBatchUpdates} method is missing in the JDBC
     * driver implementation.
     */
    public static boolean supportsBatchUpdates(DatabaseMetaData meta) {
        try {
            return meta.supportsBatchUpdates();
        } catch (Throwable e) {
            log.error("Missing DatabaseMetaData.supportsBatchUpdates method.");
            return false;
        }
    }
}
