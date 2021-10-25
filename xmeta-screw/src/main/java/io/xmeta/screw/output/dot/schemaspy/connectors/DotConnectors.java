package io.xmeta.screw.output.dot.schemaspy.connectors;

import java.util.Set;
import io.xmeta.screw.output.dot.schemaspy.DotConnector;

/**
 * Format table data into .dot format to feed to Graphvis' dot program.
 *
 * @author John Currier
 */
public interface DotConnectors {
    Set<DotConnector> unique();
}
