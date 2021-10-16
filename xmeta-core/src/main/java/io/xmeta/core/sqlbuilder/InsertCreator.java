package io.xmeta.core.sqlbuilder;

import io.xmeta.core.domain.Entity;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A Spring PreparedStatementCreator that you can use like an InsertBuilder.
 * Example usage is as follows:
 *
 * <pre>
 * PreparedStatementCreator psc = new InsertCreator(&quot;emp&quot;).setRaw(&quot;id&quot;, &quot;emp_id_seq.nextval&quot;).setValue(&quot;name&quot;,
 *         employee.getName());
 *
 * new JdbcTemplate(dataSource).update(psc);
 * </pre>
 *
 * @author John Krasnay <john@krasnay.ca>
 */
public class InsertCreator implements SqlParameterSourceCreator, SqlCreator, Serializable {

    private static final long serialVersionUID = 1;

    private InsertBuilder builder;

    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    public InsertCreator(Entity entity) {
        builder = new InsertBuilder(entity);
    }

    public SqlParameterSource createSqlParameterSource() {
        return sqlParameterSource;
    }

    public void set(String property, String value) {
        builder.set(property, value);
    }

    @Override
    public String getSQL() {
        return builder.toString();
    }

    public InsertCreator setValue(String property, Object value) {
        set(property, ":" + property);
        sqlParameterSource.addValue(property, value);
        return this;
    }

}
