package io.xmeta.core.dao;

import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;

import java.util.*;

public class SqlIdentifierParameterSource extends AbstractSqlParameterSource {
    private final IdentifierProcessing identifierProcessing;
    private final Set<SqlIdentifier> identifiers = new HashSet<>();
    private final Map<String, Object> namesToValues = new HashMap<>();

    SqlIdentifierParameterSource(IdentifierProcessing identifierProcessing) {
        this.identifierProcessing = identifierProcessing;
    }

    @Override
    public boolean hasValue(String paramName) {
        return namesToValues.containsKey(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        return namesToValues.get(paramName);
    }

    @Override
    public String[] getParameterNames() {
        return namesToValues.keySet().toArray(new String[0]);
    }

    Set<SqlIdentifier> getIdentifiers() {
        return Collections.unmodifiableSet(identifiers);
    }

    void addValue(SqlIdentifier name, Object value) {
        addValue(name, value, Integer.MIN_VALUE);
    }

    void addValue(SqlIdentifier identifier, Object value, int sqlType) {

        identifiers.add(identifier);
        String name = identifier.getReference(identifierProcessing);
        namesToValues.put(name, value);
        registerSqlType(name, sqlType);
    }

    void addAll(SqlIdentifierParameterSource others) {

        for (SqlIdentifier identifier : others.getIdentifiers()) {

            String name = identifier.getReference(identifierProcessing);
            addValue(identifier, others.getValue(name), others.getSqlType(name));
        }
    }
}
