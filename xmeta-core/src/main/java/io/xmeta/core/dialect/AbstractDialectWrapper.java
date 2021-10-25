package io.xmeta.core.dialect;

import io.xmeta.core.exception.MetaException;
import org.springframework.data.relational.core.dialect.*;
import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.data.relational.core.sql.render.SelectRenderContext;

import java.sql.Types;

public abstract class AbstractDialectWrapper<T extends Dialect> implements MetaDialect {

    private final TypeNames typeNames = new TypeNames();

    protected T dialect;

    public AbstractDialectWrapper(T dialect) {
        this.dialect = dialect;

        registerColumnType( Types.BIT, "bit" );
        registerColumnType( Types.BOOLEAN, "boolean" );
        registerColumnType( Types.TINYINT, "tinyint" );
        registerColumnType( Types.SMALLINT, "smallint" );
        registerColumnType( Types.INTEGER, "integer" );
        registerColumnType( Types.BIGINT, "bigint" );
        registerColumnType( Types.FLOAT, "float($p)" );
        registerColumnType( Types.DOUBLE, "double precision" );
        registerColumnType( Types.NUMERIC, "numeric($p,$s)" );
        registerColumnType( Types.REAL, "real" );

        registerColumnType( Types.DATE, "date" );
        registerColumnType( Types.TIME, "time" );
        registerColumnType( Types.TIMESTAMP, "timestamp" );

        registerColumnType( Types.VARBINARY, "bit varying($l)" );
        registerColumnType( Types.LONGVARBINARY, "bit varying($l)" );
        registerColumnType( Types.BLOB, "blob" );

        registerColumnType( Types.CHAR, "char($l)" );
        registerColumnType( Types.VARCHAR, "varchar($l)" );
        registerColumnType( Types.LONGVARCHAR, "varchar($l)" );
        registerColumnType( Types.CLOB, "clob" );

        registerColumnType( Types.NCHAR, "nchar($l)" );
        registerColumnType( Types.NVARCHAR, "nvarchar($l)" );
        registerColumnType( Types.LONGNVARCHAR, "nvarchar($l)" );
        registerColumnType( Types.NCLOB, "nclob" );
    }

    @Override
    public LimitClause limit() {
        return dialect.limit();
    }

    @Override
    public LockClause lock() {
        return dialect.lock();
    }

    @Override
    public ArrayColumns getArraySupport() {
        return dialect.getArraySupport();
    }

    @Override
    public SelectRenderContext getSelectContext() {
        return dialect.getSelectContext();
    }

    @Override
    public IdentifierProcessing getIdentifierProcessing() {
        return dialect.getIdentifierProcessing();
    }

    @Override
    public Escaper getLikeEscaper() {
        return dialect.getLikeEscaper();
    }

    @Override
    public IdGeneration getIdGeneration() {
        return dialect.getIdGeneration();
    }

    protected void registerColumnType(int code, String name) {
        typeNames.put( code, name );
    }

    protected void registerColumnType(int code, long capacity, String name) {
        typeNames.put( code, capacity, name );
    }

    public String getTypeName(int code) {
        final String result = typeNames.get( code );
        if ( result == null ) {
            throw new MetaException( "No default type mapping for (java.sql.Types) " + code );
        }
        return result;
    }

    public String getTypeName(int code, long length, int precision, int scale) {
        final String result = typeNames.get( code, length, precision, scale );
        if ( result == null ) {
            throw new MetaException(
                    String.format( "No type mapping for java.sql.Types code: %s, length: %s", code, length )
            );
        }
        return result;
    }
}
