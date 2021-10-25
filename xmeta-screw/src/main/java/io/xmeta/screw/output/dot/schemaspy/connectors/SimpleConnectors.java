package io.xmeta.screw.output.dot.schemaspy.connectors;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import io.xmeta.screw.model.Table;
import io.xmeta.screw.model.TableColumn;
import io.xmeta.screw.output.dot.schemaspy.DotConnector;

public class SimpleConnectors implements DotConnectors {
    private final Table table;
    private final boolean includeImplied;

    /**
     *
     * @param table Table
     * @throws IOException
     * @return Set of <code>dot</code> relationships (as {@link DotConnector}s)
     */
    public SimpleConnectors(Table table, boolean includeImplied) {
        this.table = table;
        this.includeImplied = includeImplied;
    }

        @Override
    public Set<DotConnector> unique() {
        Set<DotConnector> relationships = new HashSet<>();

        for (TableColumn column : table.getColumns()) {
            relationships.addAll(new RelatedConnectors(column, null, false, includeImplied).unique());
        }

        return relationships;
    }
}
