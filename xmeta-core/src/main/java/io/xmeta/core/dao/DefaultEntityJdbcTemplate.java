package io.xmeta.core.dao;

import io.xmeta.core.MetaException;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.convert.Identifier;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcValue;
import org.springframework.data.mapping.PersistentPropertyPath;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
public class DefaultEntityJdbcTemplate implements EntityJdbcTemplate {

    private final JdbcConverter converter;
    private final NamedParameterJdbcOperations operations;
    private final Dialect dialect;

    public DefaultEntityJdbcTemplate(JdbcConverter converter, NamedParameterJdbcOperations operations,
                                     Dialect dialect) {

        Assert.notNull(converter, "JdbcConverter must not be null");
        Assert.notNull(operations, "NamedParameterJdbcOperations must not be null");

        this.converter = converter;
        this.operations = operations;
        this.dialect = dialect;
    }

    @Override
    public Object insert(Entity entity, Map<String, Object> data, Identifier identifier) {

        SqlGenerator sqlGenerator = sql(entity);

        SqlIdentifierParameterSource parameterSource = getParameterSource(data, entity, getIdentifierProcessing());

        identifier.forEach((name, value, type) -> addConvertedPropertyValue(parameterSource, name, value, type));

        EntityField pk = EntityFieldUtils.findPK(entity).orElseThrow(MetaException::new);

        Object idValue = data.get(pk.getName());
        if (idValue != null) {
            addConvertedPropertyValue(parameterSource, SqlIdentifier.unquoted(pk.getColumn()), idValue, String.class);
        }

        String insertSql = sqlGenerator.getInsert(new HashSet<>(parameterSource.getIdentifiers()));

        log.info(insertSql);

        operations.update(insertSql, parameterSource);
        return data;
    }

    @Override
    public int update(Entity entity, Map<String, Object> data) {
        return 0;
    }

    @Override
    public int delete(Entity entity, Map<String, Object> data) {
        return 0;
    }

    @Override
    public int deleteAll(Entity entity) {
        return 0;
    }

    @Override
    public void deleteAll(PersistentPropertyPath<RelationalPersistentProperty> propertyPath) {

    }

    @Override
    public <T> void acquireLockById(Object id, LockMode lockMode, Class<T> domainType) {

    }

    @Override
    public <T> void acquireLockAll(LockMode lockMode, Class<T> domainType) {

    }

    @Override
    public long count(Class<?> domainType) {
        return 0;
    }

    @Override
    public <T> T findById(Object id, Class<T> domainType) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findAll(Entity entity) {
        String sql = sql(entity).getFindAll();
        log.info(sql);
        return operations.query(sql, getEntityRowMapper(entity));
    }

    @Override
    public <T> Iterable<T> findAllById(Iterable<?> ids, Class<T> domainType) {
        return null;
    }

    @Override
    public <T> boolean existsById(Object id, Class<T> domainType) {
        return false;
    }

    @Override
    public Iterable<Map<String, Object>> findAll(Entity entity, Sort sort) {
        return operations.query(sql(entity).getFindAll(sort), getEntityRowMapper(entity));
    }

    @Override
    public Iterable<Map<String, Object>> findAll(Entity entity, Pageable pageable) {
        return operations.query(sql(entity).getFindAll(pageable), getEntityRowMapper(entity));
    }

    private SqlIdentifierParameterSource getParameterSource(@NonNull Map<String, Object> instance, Entity entity, IdentifierProcessing identifierProcessing) {

        SqlIdentifierParameterSource parameters = new SqlIdentifierParameterSource(identifierProcessing);

        EntityUtils.doWithAll(entity, field -> {
            if (!instance.containsKey(field.getName())) {
                return;
            }
            if (field.getDataType() == DataType.Roles || field.getDataType() == DataType.Lookup) {
                return;
            }
            Object value = instance.get(field.getName());
            SqlIdentifier paramName = transform(field.getColumn());
            try {
                Class cls = ClassUtils.getClass(field.getJavaType(), false);
                addConvertedPropertyValue(parameters, paramName, value, cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

        });

        return parameters;
    }

    private SqlIdentifier transform(String name) {
        return SqlIdentifier.unquoted(name);
    }

    private void addConvertedPropertyValue(SqlIdentifierParameterSource parameterSource, SqlIdentifier name, Object value,
                                           Class<?> javaType) {

        addConvertedValue(parameterSource, value, name, javaType, JdbcUtil.sqlTypeFor(javaType));
    }

    private void addConvertedValue(SqlIdentifierParameterSource parameterSource, @Nullable Object value,
                                   SqlIdentifier paramName, Class<?> javaType, int sqlType) {

        JdbcValue jdbcValue = converter.writeJdbcValue( //
                value, //
                javaType, //
                sqlType //
        );

        parameterSource.addValue( //
                paramName, //
                jdbcValue.getValue(), //
                JdbcUtil.sqlTypeFor(jdbcValue.getJdbcType()));
    }

    private IdentifierProcessing getIdentifierProcessing() {
        return this.dialect.getIdentifierProcessing();
    }

    private SqlGenerator sql(Entity entity) {
        return new SqlGenerator(converter, entity, this.dialect);
    }

    private EntityRowMapper getEntityRowMapper(Entity entity) {
        return new EntityRowMapper(entity, converter);
    }
}
