/*
 * Copyright (C) 2004 - 2011 John Currier
 * Copyright (C) 2019 Nils Petzaell
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
package io.xmeta.screw.model;

import io.xmeta.screw.dbms.service.helper.RemoteTableIdentifier;

/**
 * A remote table (exists in another schema (logically or physically))
 * that was created via XML metadata.
 *
 * @author John Currier
 * @author Nils Petzaell
 */
public class LogicalRemoteTable extends RemoteTable {

    public LogicalRemoteTable(
            Database database,
            RemoteTableIdentifier remoteTableIdentifier,
            String baseSchema) {
        super(database,
                remoteTableIdentifier,
                baseSchema);
    }

    /**
     * Don't attempt to query our metadata from the database.
     *
     * @return true
     */
    @Override
    public boolean isLogical() {
        return true;
    }
}
