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

import io.xmeta.screw.model.Table;
import io.xmeta.screw.output.diagram.DiagramException;
import io.xmeta.screw.output.dot.schemaspy.DotFormatter;
import io.xmeta.screw.util.Writers;
import io.xmeta.screw.view.FileNameGenerator;
import io.xmeta.screw.view.MustacheTableDiagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Nils Petzaell
 */
public class MustacheOrphanDiagramFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DotFormatter dotProducer;
    private final MustacheDiagramFactory mustacheDiagramFactory;
    private final Path orphanDir;

    public MustacheOrphanDiagramFactory(DotFormatter dotProducer, MustacheDiagramFactory mustacheDiagramFactory, File outputDir) {
        this.dotProducer = dotProducer;
        this.mustacheDiagramFactory = mustacheDiagramFactory;
        orphanDir = outputDir.toPath().resolve("diagrams").resolve("orphans");
    }

    public List<MustacheTableDiagram> generateOrphanDiagrams(List<Table> orphanTables) {

        Collections.sort(orphanTables, (Comparator) (t1, t2) -> {
            Integer size1 = ((Table) t1).getColumns().size();
            Integer size2 = ((Table) t2).getColumns().size();
            int sizeComp = size1.compareTo(size2);

            if (sizeComp != 0) {
                return sizeComp;
            } else {
                String name1 = ((Table) t1).getName();
                String name2 = ((Table) t1).getName();
                return name1.compareTo(name2);
            }
        });

        List<MustacheTableDiagram> mustacheTableDiagrams = new ArrayList<>();
        for(Table table : orphanTables) {
            String dotBaseFilespec = FileNameGenerator.generate(table.getName());

            File dotFile = orphanDir.resolve(dotBaseFilespec + ".1degree.dot").toFile();

            try (PrintWriter dotOut = Writers.newPrintWriter(dotFile)) {
                dotProducer.writeOrphan(table, dotOut);
                mustacheTableDiagrams.add(mustacheDiagramFactory.generateOrphanDiagram(dotBaseFilespec, dotFile, dotBaseFilespec + ".1degree"));
            } catch (IOException e) {
                LOGGER.error("Failed to produce dot: {}", dotFile, e);
            } catch (DiagramException e) {
                LOGGER.error("Failed to produce diagram for: {}", dotFile, e);
            }
        }
        return mustacheTableDiagrams;
    }
}
