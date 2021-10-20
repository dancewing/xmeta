package io.xmeta.core.dao;

import io.xmeta.core.domain.Entity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.convert.Identifier;
import org.springframework.data.mapping.PersistentPropertyPath;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface EntityJdbcTemplate {

    @Nullable
    Object insert(Entity entity, Map<String, Object> data, Identifier identifier);

    int update(Entity entity, Map<String, Object> data, Map<String, Object> where);

    int deleteById(Entity entity, Object id);

    int delete(Entity entity, Map<String, Object> where);

    int deleteAll(Entity entity);

    /**
     * Deletes all entities reachable via {@literal propertyPath} from any instance.
     *
     * @param propertyPath Leading from the root object to the entities to be deleted. Must not be {@code null}.
     */
    void deleteAll(PersistentPropertyPath<RelationalPersistentProperty> propertyPath);

    /**
     * Acquire a lock on the aggregate specified by id.
     *
     * @param id         the id of the entity to load. Must not be {@code null}.
     * @param lockMode   the lock mode for select. Must not be {@code null}.
     * @param domainType the domain type of the entity. Must not be {@code null}.
     */
    <T> void acquireLockById(Object id, LockMode lockMode, Class<T> domainType);

    /**
     * Acquire a lock on all aggregates of the given domain type.
     *
     * @param lockMode   the lock mode for select. Must not be {@code null}.
     * @param domainType the domain type of the entity. Must not be {@code null}.
     */
    <T> void acquireLockAll(LockMode lockMode, Class<T> domainType);

    /**
     * Counts the rows in the table representing the given domain type.
     *
     * @param domainType the domain type for which to count the elements. Must not be {@code null}.
     * @return the count. Guaranteed to be not {@code null}.
     */
    long count(Class<?> domainType);

    /**
     * Loads a single entity identified by type and id.
     *
     * @param id         the id of the entity to load. Must not be {@code null}.
     * @param domainType the domain type of the entity. Must not be {@code null}.
     * @param <T>        the type of the entity.
     * @return Might return {@code null}.
     */
    @Nullable
    <T> T findById(Object id, Class<T> domainType);

    List<Map<String, Object>> findAll(Entity entity);

    /**
     * Loads all entities that match one of the ids passed as an argument. It is not guaranteed that the number of ids
     * passed in matches the number of entities returned.
     *
     * @param ids        the Ids of the entities to load. Must not be {@code null}.
     * @param domainType the type of entities to load. Must not be {@code null}.
     * @param <T>        type of entities to load.
     * @return the loaded entities. Guaranteed to be not {@code null}.
     */
    <T> Iterable<T> findAllById(Iterable<?> ids, Class<T> domainType);

    /**
     * returns if a row with the given id exists for the given type.
     *
     * @param id         the id of the entity for which to check. Must not be {@code null}.
     * @param domainType the type of the entity to check for. Must not be {@code null}.
     * @param <T>        the type of the entity.
     * @return {@code true} if a matching row exists, otherwise {@code false}.
     */
    <T> boolean existsById(Object id, Class<T> domainType);


    List<Map<String, Object>> findAll(Entity entity, Sort sort);


    List<Map<String, Object>> findAll(Entity entity, Pageable pageable);
}
