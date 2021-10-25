/*
 * Copyright (C) 2018 Nils Petzaell
 *
 * This file is part of SchemaSpy.
 *
 * SchemaSpy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SchemaSpy. If not, see <http://www.gnu.org/licenses/>.
 */
package io.xmeta.screw.analyzer;

import io.xmeta.screw.DbAnalyzer;
import io.xmeta.screw.model.ImpliedForeignKeyConstraint;
import io.xmeta.screw.model.Table;

import java.util.Collection;
import java.util.List;

public class ImpliedConstraintsFinder {
    public List<ImpliedForeignKeyConstraint> find(Collection<Table> tables) {
        return DbAnalyzer.getImpliedConstraints(tables);
    }
}
