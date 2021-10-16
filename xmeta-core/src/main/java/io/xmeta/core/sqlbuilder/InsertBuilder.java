package io.xmeta.core.sqlbuilder;

import io.xmeta.core.MetaException;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder for building SQL insert statements.
 *
 * @author John Krasnay <john@krasnay.ca>
 */
public class InsertBuilder extends AbstractSqlBuilder implements Serializable {

    private static final long serialVersionUID = 1;

    private String table;

    private Entity entity;

    private Map<String, EntityField> fields = new HashMap<>();

    private List<String> columns = new ArrayList<>();
    private List<String> values = new ArrayList<>();

    public InsertBuilder(Entity entity) {
        this.entity = entity;
        this.table = entity.getTable();
        this.entity.getFields().forEach(entityField -> {
            this.fields.put(entityField.getName(), entityField);
        });
    }

    /**
     * Inserts a property name, value pair into the SQL.
     *
     * @param property
     *            Name of the entity property.
     * @param value
     *            Value to substitute in. InsertBuilder does *no* interpretation
     *            of this. If you want a string constant inserted, you must
     *            provide the single quotes and escape the internal quotes. It
     *            is more common to use a question mark or a token in the style
     *            of {@link ParameterizedPreparedStatementCreator}, e.g. ":foo".
     */
    public InsertBuilder set(String property, String value) {
        if (this.fields.containsKey(property)) {
            columns.add(this.fields.get(property).getColumn());
            values.add(value);
            return this;
        }
        throw new MetaException("cant find field from entity: " + entity.getName());
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("insert into ").append(table).append(" (");
        appendList(sql, columns, "", ", ");
        sql.append(") values (");
        appendList(sql, values, "", ", ");
        sql.append(")");
        return sql.toString();
    }
}
