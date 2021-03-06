/*
 * Copyright (C) 2019 Nils Petzaell
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
package io.xmeta.screw.cli;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

public class DegreeOfSeparationValidator implements IValueValidator<Integer> {
    @Override
    public void validate(String name, Integer value) {
        if (value > 2 || value < 1 ) {
            throw new ParameterException("Illegal value for '"+name+"', allowed values are ['1','2']");
        }
    }
}
