package io.xmeta.screw.output.dot.schemaspy.columnsfilter.factory;

import io.xmeta.screw.model.TableColumn;
import io.xmeta.screw.output.dot.schemaspy.columnsfilter.Columns;

public interface Factory {
    Columns columns();
    Columns children(TableColumn column);
    Columns parents(TableColumn column);
}
