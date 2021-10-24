package io.xmeta.core.utils.jdbc.oracle;

import io.xmeta.core.exception.MetaException;

public class OracleConnectionStringException extends MetaException {

    public OracleConnectionStringException() {
    }

    public OracleConnectionStringException(String message) {
        super(message);
    }
}
