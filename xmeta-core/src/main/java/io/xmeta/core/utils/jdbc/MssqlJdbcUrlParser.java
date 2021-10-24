/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.xmeta.core.utils.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MssqlJdbcUrlParser implements JdbcUrlParser {

    public static final MssqlJdbcUrlParser INSTANCE = new MssqlJdbcUrlParser();

    //    jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]
    private static final String MSSQL_URL_PREFIX = "jdbc:sqlserver:";

    // https://docs.microsoft.com/ko-kr/sql/connect/jdbc/setting-the-connection-properties?view=sql-server-ver15#remarks
    // Property names are case-insensitive
    private static final String DATABASE_NAME_PROPERTY = "databaseName=";

    @Override
    public DatabaseInfo parse(String jdbcUrl) {
        if (jdbcUrl == null) {
            log.info("jdbcUrl");
            return UnKnownDatabaseInfo.INSTANCE;
        }
        if (!jdbcUrl.startsWith(MSSQL_URL_PREFIX)) {
            log.info("jdbcUrl has invalid prefix.(url:{}, prefix:{})", jdbcUrl, MSSQL_URL_PREFIX);
            return UnKnownDatabaseInfo.INSTANCE;
        }


        try {
            return parse0(jdbcUrl);
        } catch (Exception e) {
            log.info("MssqlJdbcUrl parse error. url: {}, Caused: {}", jdbcUrl, e.getMessage(), e);
            return UnKnownDatabaseInfo.INSTANCE;
        }
    }


    private DatabaseInfo parse0(String url) {
        // jdbc:sqlserver://localhost;databaseName=AdventureWorks;integratedSecurity=true;applicationName=MyApp;
        StringMaker maker = new StringMaker(url);
        maker.lower();

        final String databaseNamePropertyLowerCase = DATABASE_NAME_PROPERTY.toLowerCase();

        maker.after(MSSQL_URL_PREFIX);

        String host = maker.after("//").before(';').value();
        List<String> hostList = new ArrayList<>(1);
        hostList.add(host);
        // String port = maker.next().after(':').before(';').value();

        String databaseId = maker.next().after(databaseNamePropertyLowerCase).before(';').value();
        String normalizedUrl =
                maker.clear().before(databaseNamePropertyLowerCase).value() + DATABASE_NAME_PROPERTY + databaseId;
        return new DefaultDatabaseInfo(url,
                normalizedUrl, hostList, databaseId);
    }

}
