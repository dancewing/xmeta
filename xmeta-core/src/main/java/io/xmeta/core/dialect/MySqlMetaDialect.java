package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.MySqlDialect;

import java.sql.Types;


public class MySqlMetaDialect extends AbstractDialectWrapper<MySqlDialect> {

    public MySqlMetaDialect(MySqlDialect dialect) {
        super(dialect);

        registerColumnType( Types.BIT, "bit" );
        registerColumnType( Types.BIGINT, "bigint" );
        registerColumnType( Types.SMALLINT, "smallint" );
        registerColumnType( Types.TINYINT, "tinyint" );
        registerColumnType( Types.INTEGER, "integer" );
        registerColumnType( Types.CHAR, "char(1)" );
        registerColumnType( Types.FLOAT, "float" );
        registerColumnType( Types.DOUBLE, "double precision" );
        registerColumnType( Types.BOOLEAN, "bit" ); // HHH-6935
        registerColumnType( Types.DATE, "date" );
        registerColumnType( Types.TIME, "time" );
        registerColumnType( Types.TIMESTAMP, "datetime" );
        registerColumnType( Types.VARBINARY, "longblob" );
        registerColumnType( Types.VARBINARY, 16777215, "mediumblob" );
        registerColumnType( Types.VARBINARY, 65535, "blob" );
        registerColumnType( Types.VARBINARY, 255, "tinyblob" );
        registerColumnType( Types.BINARY, "binary($l)" );
        registerColumnType( Types.LONGVARBINARY, "longblob" );
        registerColumnType( Types.LONGVARBINARY, 16777215, "mediumblob" );
        registerColumnType( Types.NUMERIC, "decimal($p,$s)" );
        registerColumnType( Types.BLOB, "longblob" );
//		registerColumnType( Types.BLOB, 16777215, "mediumblob" );
//		registerColumnType( Types.BLOB, 65535, "blob" );
        registerColumnType( Types.CLOB, "longtext" );
        registerColumnType( Types.NCLOB, "longtext" );

        registerVarcharTypes();
    }

    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    public String getTableComment(String comment) {
        return " comment='" + comment + "'";
    }

    @Override
    public String getColumnComment(String comment) {
        return " comment '" + comment + "'";
    }

    public String getTableTypeString() {
        // grrr... for differentiation of mysql storage engines
        return " ENGINE = InnoDB ";
    }

    protected void registerVarcharTypes() {
        registerColumnType( Types.VARCHAR, "longtext" );
//		registerColumnType( Types.VARCHAR, 16777215, "mediumtext" );
        registerColumnType( Types.VARCHAR, 65535, "varchar($l)" );
        registerColumnType( Types.LONGVARCHAR, "longtext" );
    }

    public String getAddColumnString() {
        return "add column";
    }

}
