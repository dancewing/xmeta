package io.xmeta.core.sqlbuilder;

import io.xmeta.core.sqlbuilder.orm.DefaultConverterFactory;

import javax.sql.DataSource;

/**
 * Configuration of the ORM system. Each mapping must be constructed with one of
 * these objects.
 *
 * @author <a href="mailto:john@krasnay.ca">John Krasnay</a>
 */
public class MetaRuntime {

    private DataSource dataSource;
    private Dialect dialect;
    private boolean supportsBatchUpdates = false;
    private boolean supportsGetGeneratedKeys = false;
    private boolean showSQL = true;

    private ConverterFactory converterFactory = new DefaultConverterFactory();

    public MetaRuntime(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public Supplier<Integer> getSequence(String sequenceName) {
        return dialect.getSequence(dataSource, sequenceName);
    }

    public MetaRuntime setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public MetaRuntime setDialect(Dialect dialect) {
        this.dialect = dialect;
        return this;
    }

    public MetaRuntime setSupportsBatchUpdates(boolean supportsBatchUpdates) {
        this.supportsBatchUpdates = supportsBatchUpdates;
        return this;
    }

    public MetaRuntime setSupportsGetGeneratedKeys(boolean supportsGetGeneratedKeys) {
        this.supportsGetGeneratedKeys = supportsGetGeneratedKeys;
        return this;
    }

    public boolean isSupportsBatchUpdates() {
        return supportsBatchUpdates;
    }

    public boolean isSupportsGetGeneratedKeys() {
        return supportsGetGeneratedKeys;
    }

    public boolean isShowSQL() {
        return showSQL;
    }
}
