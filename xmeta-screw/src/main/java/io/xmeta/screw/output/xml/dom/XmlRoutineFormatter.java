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
package io.xmeta.screw.output.xml.dom;

import io.xmeta.screw.model.Routine;
import io.xmeta.screw.model.RoutineParameter;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import java.util.*;

public class XmlRoutineFormatter {

    public void appendRoutines(Element schemaNode, Collection<Routine> routines) {
        LinkedList<Routine> routinesList = new LinkedList<>(routines);
        Collections.sort(routinesList, Comparator.comparing(Routine::getName));
        if (!routinesList.isEmpty()) {
            Element routinesElement = schemaNode.getOwnerDocument().createElement("routines");
            schemaNode.appendChild(routinesElement);
            for( Routine routine : routinesList) {
                appendRoutine(routinesElement, routine);
            }
        }
    }

    private static void appendRoutine(Element routinesElement, Routine routine) {
        Element routineElement = routinesElement.getOwnerDocument().createElement("routine");
        routinesElement.appendChild(routineElement);
        DOMUtil.appendAttribute(routineElement, "name", routine.getName());
        DOMUtil.appendAttribute(routineElement, "type", routine.getType());
        DOMUtil.appendAttribute(routineElement, "returnType", routine.getReturnType());
        DOMUtil.appendAttribute(routineElement, "dataAccess", routine.getDataAccess());
        DOMUtil.appendAttribute(routineElement, "securityType", routine.getSecurityType());
        DOMUtil.appendAttribute(routineElement, "deterministic", Boolean.toString(routine.isDeterministic()));
        Element commentElement = routinesElement.getOwnerDocument().createElement("comment");
        routineElement.appendChild(commentElement);
        if (notNullOrEmpty(routine.getComment())) {
           CDATASection commentCDATA = routinesElement.getOwnerDocument().createCDATASection(routine.getComment());
           commentElement.appendChild(commentCDATA);
        }
        Element definitionElement = routinesElement.getOwnerDocument().createElement("definition");
        routineElement.appendChild(definitionElement);
        if (notNullOrEmpty(routine.getDefinitionLanguage())) {
            DOMUtil.appendAttribute(definitionElement, "language", routine.getDefinitionLanguage());
        }
        if (notNullOrEmpty(routine.getDefinition())) {
            CDATASection definitionCDATA = routinesElement.getOwnerDocument().createCDATASection(routine.getDefinition());
            definitionElement.appendChild(definitionCDATA);
        }
        Element parametersElement = routineElement.getOwnerDocument().createElement("parameters");
        routineElement.appendChild(parametersElement);
        if (!routine.getParameters().isEmpty()) {
            appendParameters(parametersElement, routine.getParameters());
        }
    }

    private static void appendParameters(Element parametersElement, List<RoutineParameter> routineParameters) {
        for (RoutineParameter parameter : routineParameters) {
            Element parameterElement = parametersElement.getOwnerDocument().createElement("parameter");
            parametersElement.appendChild(parameterElement);
            if (notNullOrEmpty(parameter.getName())) {
                DOMUtil.appendAttribute(parameterElement, "name", parameter.getName());
            }
            DOMUtil.appendAttribute(parameterElement, "type" , parameter.getType());
            DOMUtil.appendAttribute(parameterElement, "mode" , parameter.getMode());
        }
    }

    private static boolean notNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
