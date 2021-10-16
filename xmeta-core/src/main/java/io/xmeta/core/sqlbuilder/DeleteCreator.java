package io.xmeta.core.sqlbuilder;


import io.xmeta.core.domain.Entity;

/**
 * A Spring PreparedStatementCreator that you can use like a DeleteBuilder.
 * Example usage is as follows:
 *
 * <pre>
 * PreparedStatementCreator psc = new DeleteCreator(&quot;emp&quot;).whereEquals(&quot;id&quot;,
 *         employeeId);
 *
 * new JdbcTemplate(dataSource).update(psc);
 * </pre>
 *
 * @author John Krasnay <john@krasnay.ca>
 */
public class DeleteCreator extends AbstractSqlCreator {

    private static final long serialVersionUID = 1;

    private DeleteBuilder builder;

//    public DeleteCreator(String table) {
//        super(null);
//        builder = new DeleteBuilder(table);
//    }

    public DeleteCreator(Entity entity) {
        super(entity);
        builder = new DeleteBuilder(entity);
    }

    @Override
    protected AbstractSqlBuilder getBuilder() {
        return builder;
    }

    public DeleteCreator where(String expr) {
        builder.where(expr);
        return this;
    }

    public DeleteCreator where(Predicate predicate) {
        predicate.init(this);
        builder.where(predicate.toSql());
        return this;
    }

    public DeleteCreator whereEquals(String expr, Object value) {

        String param = allocateParameter();

        builder.where(expr + " = :" + param);
        setParameter(param, value);

        return this;
    }

}
