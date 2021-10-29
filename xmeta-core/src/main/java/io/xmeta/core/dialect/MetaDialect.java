package io.xmeta.core.dialect;

import org.springframework.data.relational.core.dialect.Dialect;

import java.sql.Types;

/**
 * Represents a dialect that is implemented by a particular database. Please note that not all features are supported by
 * all vendors. Dialects typically express this with feature flags. Methods for unsupported functionality may throw
 * {@link UnsupportedOperationException}.
 *
 * @author Mark Paluch
 * @author Jens Schauder
 * @author Myeonghyeon Lee
 * @since 1.1
 */
public interface MetaDialect extends Dialect {

    default boolean supportsIfExistsBeforeTableName() {
        return false;
    }

    default boolean supportsIfExistsAfterTableName() {
        return false;
    }

    default boolean supportsIfExistsAfterAlterTable() {
        return false;
    }

    default String getCascadeConstraintsString() {
        return "";
    }

    default String getCreateTableString() {
        return "create table";
    }

    default String getCreateMultisetTableString() {
        return getCreateTableString();
    }

    default String getAlterTableString(String tableName) {
        final StringBuilder sb = new StringBuilder( "alter table " );
        if ( supportsIfExistsAfterAlterTable() ) {
            sb.append( "if exists " );
        }
        sb.append( tableName );
        return sb.toString();
    }

    default String getAddColumnString() {
        throw new UnsupportedOperationException( "No add column syntax supported by " + getClass().getName() );
    }

    default String getAddColumnSuffixString() {
        return "";
    }

    default String getNullColumnString() {
        return "";
    }

    default String getColumnComment(String comment) {
        return "";
    }

    default String getTableTypeString() {
        // grrr... for differentiation of mysql storage engines
        return "";
    }

    default String getTableComment(String comment) {
        return "";
    }

    default  String getDropTableString(String tableName) {
        final StringBuilder buf = new StringBuilder( "drop table " );
        if ( supportsIfExistsBeforeTableName() ) {
            buf.append( "if exists " );
        }
        buf.append( tableName ).append( getCascadeConstraintsString() );
        if ( supportsIfExistsAfterTableName() ) {
            buf.append( " if exists" );
        }
        return buf.toString();
    }

    String getTypeName(int code);

    /**
     * Get the name of the database type associated with the given
     * {@link Types} typecode with the given storage specification
     * parameters.
     *
     * @param code The {@link Types} typecode
     * @param length The datatype length
     * @param precision The datatype precision
     * @param scale The datatype scale
     * @return the database type name
     */
    String getTypeName(int code, long length, int precision, int scale);


}
