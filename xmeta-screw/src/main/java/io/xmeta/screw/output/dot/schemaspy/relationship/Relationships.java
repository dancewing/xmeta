package io.xmeta.screw.output.dot.schemaspy.relationship;

import java.util.Set;
import io.xmeta.screw.model.ForeignKeyConstraint;

/**
 * Abstracts relationships associated with a table.
 */
public interface Relationships {

    /**
     * Writes relationships associated with a table.
     * @return A set of the implied constraints that could have been included but weren't.
     */
    Set<ForeignKeyConstraint> write();
}
