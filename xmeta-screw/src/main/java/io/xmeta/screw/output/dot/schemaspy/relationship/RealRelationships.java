package io.xmeta.screw.output.dot.schemaspy.relationship;

import java.io.PrintWriter;
import java.util.Set;
import io.xmeta.screw.model.ForeignKeyConstraint;
import io.xmeta.screw.model.Table;
import io.xmeta.screw.output.dot.DotConfig;
import io.xmeta.screw.output.dot.schemaspy.DotFormat;
import io.xmeta.screw.output.dot.schemaspy.DotTableFormatter;
import io.xmeta.screw.view.WriteStats;

/**
 * Represents real relationships (excluding implied) associated with a table.
 */
public final class RealRelationships implements Relationships {

    private final Relationships origin;

    public RealRelationships(
        final DotFormat dotFormat,
        final DotConfig dotConfig,
        final Table table,
        final boolean twoDegreesOfSeparation,
        final WriteStats stats,
        final PrintWriter dot
    ) {
        this(
            new DotTableFormatter(
                dotFormat,
                dotConfig,
                table,
                twoDegreesOfSeparation,
                stats,
                false,
                dot)
        );
    }

    public RealRelationships(final Relationships origin) {
        this.origin = origin;
    }

    @Override
    public Set<ForeignKeyConstraint> write() {
        return origin.write();
    }
}
