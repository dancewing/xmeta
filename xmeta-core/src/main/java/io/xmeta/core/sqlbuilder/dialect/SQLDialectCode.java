/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package io.xmeta.core.sqlbuilder.dialect;

public enum SQLDialectCode {
    UNKNOWN_DIALECT, POSTGRES_DIALECT, MYSQL_DIALECT, ORACLE_DIALECT, MSSQL_DIALECT, HSQL_DIALECT, H2_DIALECT, SYBASE_SQLANYWHERE_DIALECT, SQLITE_DIALECT;
}