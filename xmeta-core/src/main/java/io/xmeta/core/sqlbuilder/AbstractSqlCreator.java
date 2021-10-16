package io.xmeta.core.sqlbuilder;

import io.xmeta.core.domain.Entity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.io.Serializable;

/**
 * Abstract base class of SQL creator classes.
 *
 * @author <a href="mailto:john@krasnay.ca">John Krasnay</a>
 */
public abstract class AbstractSqlCreator implements SqlParameterSourceCreator, SqlCreator, Serializable  {

    private int paramIndex;

    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    private Entity entity;

    public AbstractSqlCreator(Entity entity) {
        this.entity = entity;
    }
    /**
     * Allocate and return a new parameter that is unique within this
     * SelectCreator. The parameter is of the form "paramN", where N is an
     * integer that is incremented each time this method is called.
     */
    public String allocateParameter() {
        return "param" + paramIndex++;
    }

    /**
     * Returns the builder for this creator.
     */
    protected abstract AbstractSqlBuilder getBuilder();

    /**
     * Sets a parameter for the creator.
     */
    public AbstractSqlCreator setParameter(String name, Object value) {
        sqlParameterSource.addValue(name, value);
        return this;
    }

    @Override
    public String getSQL() {
        return toString();
    }

    @Override
    public SqlParameterSource createSqlParameterSource() {
        return sqlParameterSource;
    }

    @Override
    public String toString() {
        return getBuilder().toString();
    }

}
