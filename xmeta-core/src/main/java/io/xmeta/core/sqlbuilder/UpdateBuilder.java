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
 * Builder for creating SQL update statements.
 *
 * @author John Krasnay <john@krasnay.ca>
 */
public class UpdateBuilder extends AbstractSqlBuilder implements Serializable {

    private static final long serialVersionUID = 1;

    private Entity entity;
    private String table;

    private List<String> sets = new ArrayList<String>();

    private List<String> wheres = new ArrayList<String>();

    private Map<String, EntityField> fields = new HashMap<>();

    public UpdateBuilder(String table) {
        this.table = table;
    }

    public UpdateBuilder(Entity entity) {
        this.entity = entity;
        this.table = entity.getTable();
        this.entity.getFields().forEach(entityField -> {
            this.fields.put(entityField.getName(), entityField);
        });
    }

    public UpdateBuilder setRaw(String expr) {
        sets.add(expr);
        return this;
    }

    public UpdateBuilder set(String property) {
        if (this.fields.containsKey(property)) {
            sets.add(this.fields.get(property).getColumn() + " = :" + property);
            return this;
        }
        throw new MetaException("cant find field from entity: " + entity.getName());
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("update ").append(table);
        appendList(sql, sets, " set ", ", ");
        appendList(sql, wheres, " where ", " and ");
        return sql.toString();
    }

    public UpdateBuilder where(String expr) {
        wheres.add(expr);
        return this;
    }

}
