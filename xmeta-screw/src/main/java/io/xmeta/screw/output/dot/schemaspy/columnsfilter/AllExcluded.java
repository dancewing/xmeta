package io.xmeta.screw.output.dot.schemaspy.columnsfilter;

import java.util.Collection;
import java.util.stream.Collectors;
import io.xmeta.screw.model.TableColumn;

public class AllExcluded implements Columns {

    private final Columns origin;

    public AllExcluded(Columns origin) {
        this.origin = origin;
    }

    @Override
    public Collection<TableColumn> value() {
        return this.origin.value().stream()
            .filter(column -> !column.isAllExcluded())
            .collect(Collectors.toList());
    }
}
