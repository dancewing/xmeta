/*
 * Copyright (C) 2004-2011 John Currier
 * Copyright (C) 2016, 2017 Rafal Kasa
 * Copyright (C) 2016, 2017 Ismail Simsek
 * Copyright (C) 2017 Wojciech Kasa
 * Copyright (C) 2017 Thomas Traude
 * Copyright (C) 2017, 2018 Nils Petzaell
 * Copyright (C) 2017 Daniel Watt
 *
 * This file is a part of the SchemaSpy project (http://schemaspy.org).
 *
 * SchemaSpy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.schemaspy;

import org.schemaspy.input.dbms.CatalogResolver;
import org.schemaspy.input.dbms.SchemaResolver;
import org.schemaspy.input.dbms.service.DatabaseService;
import org.schemaspy.input.dbms.service.SqlService;
import org.schemaspy.input.dbms.xml.SchemaMeta;
import org.schemaspy.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author John Currier
 * @author Rafal Kasa
 * @author Ismail Simsek
 * @author Wojciech Kasa
 * @author Thomas Traude
 * @author Nils Petzaell
 * @author Daniel Watt
 */
public class SchemaLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int SECONDS_IN_MS = 1000;
    private static final String DOT_HTML = ".html";
    private static final String INDEX_DOT_HTML = "index.html";


    private final SqlService sqlService;

    private final DatabaseService databaseService;

    public SchemaLoader(SqlService sqlService, DatabaseService databaseService) {
        this.sqlService = Objects.requireNonNull(sqlService);
        this.databaseService = Objects.requireNonNull(databaseService);
    }

    public Database analyze(Config config, String schema) throws SQLException, IOException {
        ProgressListener progressListener = new ConsoleSilenceProgressListener(true);
        return analyze(schema, config, progressListener);
    }

    /**
     * Copy / paste from Database, but we can't use Database here...
     *
     * @param meta DatabaseMetaData
     * @return String
     */
    private static String getDatabaseProduct(DatabaseMetaData meta) {
        try {
            return meta.getDatabaseProductName() + " - " + meta.getDatabaseProductVersion();
        } catch (SQLException exc) {
            return "";
        }
    }

    public Database analyze(String schema, Config config, ProgressListener progressListener) throws SQLException, IOException {
        try {
            LOGGER.info("Starting schema analysis");

            String dbName = config.getDb();

            DatabaseMetaData databaseMetaData = sqlService.connect(config);
            DbmsMeta dbmsMeta = sqlService.getDbmsMeta();

            LOGGER.debug("supportsSchemasInTableDefinitions: {}", databaseMetaData.supportsSchemasInTableDefinitions());
            LOGGER.debug("supportsCatalogsInTableDefinitions: {}", databaseMetaData.supportsCatalogsInTableDefinitions());

            // set default Catalog and Schema of the connection
            String catalog = new CatalogResolver(databaseMetaData).resolveCatalog(null);
            schema = new SchemaResolver(databaseMetaData).resolveSchema(schema);

            SchemaMeta schemaMeta = config.getMeta() == null ? null : new SchemaMeta(config.getMeta(), dbName, schema);
            //
            // create our representation of the database
            //
            Database db = new Database(dbmsMeta, dbName, catalog, schema);
            databaseService.gatherSchemaDetails(config, db, schemaMeta, progressListener);

            Collection<Table> tables = new ArrayList<>(db.getTables());
            tables.addAll(db.getViews());

            if (tables.isEmpty()) {
                dumpNoTablesMessage(schema, config.getUser(), databaseMetaData, config.getTableInclusions() != null);
                if (!config.isOneOfMultipleSchemas()) // don't bail if we're doing the whole enchilada
                    throw new EmptySchemaException();
            }

            List<ForeignKeyConstraint> recursiveConstraints = new ArrayList<>();

            // create an orderer to be able to determine insertion and deletion ordering of tables
            TableOrderer orderer = new TableOrderer();

            // side effect is that the RI relationships get trashed
            // also populates the recursiveConstraints collection
            List<Table> orderedTables = orderer.getTablesOrderedByRI(db.getTables(), recursiveConstraints);

            return db;
        } catch (Config.MissingRequiredParameterException missingParam) {
            config.dumpUsage(missingParam.getMessage(), missingParam.isDbTypeSpecific());
            return null;
        }
    }

    /**
     * dumpNoDataMessage
     *
     * @param schema String
     * @param user   String
     * @param meta   DatabaseMetaData
     */
    private static void dumpNoTablesMessage(String schema, String user, DatabaseMetaData meta, boolean specifiedInclusions) throws SQLException {
        LOGGER.warn("No tables or views were found in schema '{}'.", schema);
        List<String> schemas;
        try {
            schemas = DbAnalyzer.getSchemas(meta);
        } catch (SQLException | RuntimeException exc) {
            LOGGER.error("The user you specified '{}' might not have rights to read the database metadata.", user, exc);
            return;
        }

        if (Objects.isNull(schemas)) {
            LOGGER.error("Failed to retrieve any schemas");
        } else if (schemas.contains(schema)) {
            LOGGER.error(
                    "The schema exists in the database, but the user you specified '{}'" +
                    "might not have rights to read its contents.",
                    user);
            if (specifiedInclusions) {
                LOGGER.error(
                        "Another possibility is that the regular expression that you specified " +
                        "for what to include (via -i) didn't match any tables.");
            }
        } else {
            LOGGER.error(
                    "The schema '{}' could not be read/found, schema is specified using the -s option." +
                    "Make sure user '{}' has the correct privileges to read the schema." +
                    "Also not that schema names are usually case sensitive.",
                    schema, user);
            LOGGER.info(
                    "Available schemas(Some of these may be user or system schemas):{}{}",
                    System.lineSeparator(),
                    schemas.stream().collect(Collectors.joining(System.lineSeparator())));
            List<String> populatedSchemas = DbAnalyzer.getPopulatedSchemas(meta);
            if (populatedSchemas.isEmpty()) {
                LOGGER.error("Unable to determine if any of the schemas contain tables/views");
            } else {
                LOGGER.info("Schemas with tables/views visible to '{}':{}{}",
                        user,
                        System.lineSeparator(),
                        populatedSchemas.stream().map(s -> String.format("'%s'", s)).collect(Collectors.joining(System.lineSeparator())));
            }
        }
    }
}
