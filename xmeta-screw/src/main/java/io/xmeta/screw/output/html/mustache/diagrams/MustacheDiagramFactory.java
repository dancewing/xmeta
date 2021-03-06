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
package io.xmeta.screw.output.html.mustache.diagrams;

import io.xmeta.screw.output.diagram.DiagramFactory;
import io.xmeta.screw.output.diagram.DiagramResults;
import io.xmeta.screw.view.MustacheTableDiagram;

import java.io.File;

/**
 * @author Nils Petzaell
 */
public class MustacheDiagramFactory {

    private final DiagramFactory diagramFactory;

    public MustacheDiagramFactory(DiagramFactory diagramFactory) {
        this.diagramFactory = diagramFactory;
    }

    public MustacheTableDiagram generateOrphanDiagram(String name, File dotFile, String diagramName) {
        DiagramResults results = diagramFactory.generateOrphanDiagram(dotFile, diagramName);
        return createMustacheTableDiagram(name, results);
    }

    public MustacheTableDiagram generateTableDiagram(String name, File dotFile, String diagramName) {
        DiagramResults results = diagramFactory.generateTableDiagram(dotFile, diagramName);
        return createMustacheTableDiagram(name, results);
    }

    public MustacheTableDiagram generateSummaryDiagram(String name, File dotFile, String diagramName) {
        DiagramResults results = diagramFactory.generateSummaryDiagram(dotFile, diagramName);
        return createMustacheTableDiagram(name, results);
    }

    private static MustacheTableDiagram createMustacheTableDiagram(String diagramName, DiagramResults diagramResults) {
        MustacheTableDiagram mustacheTableDiagram = new MustacheTableDiagram();
        mustacheTableDiagram.setName(diagramName);
        mustacheTableDiagram.setId(diagramName.replaceAll("\\s", "").toLowerCase() + "DegreeImg");
        mustacheTableDiagram.setFileName(diagramResults.getDiagramFile().getName());
        mustacheTableDiagram.setMap(diagramResults.getDiagramMap());
        mustacheTableDiagram.setMapName(diagramResults.getDiagramMapName());
        mustacheTableDiagram.setEmbed(diagramResults.getImageFormat().equalsIgnoreCase("svg"));
        return mustacheTableDiagram;
    }

}
