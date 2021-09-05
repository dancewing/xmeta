package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.BaseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class BaseService <R extends JpaRepository<T, ID>, T extends BaseEntity, ID extends Serializable>
        implements CrudService<T, ID> {

    private final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    @Override
    public T create(T resource) {
        return repository.save(resource);
    }

    @Override
    public T update(ID id, T resource) {
        return update(id, resource, "id");
    }

    public T update(ID id, T resource, String key) {
        if (id == null) {
            throw new RuntimeException("Invalid parameter ID");
        }

        return repository.save(resource);
    }

    @Override
    public T getById(ID id) {
        if (id == null) {
            throw new RuntimeException("Invalid parameter ID");
        }
        return repository.getById(id);
    }

    @Override
    public void delete(ID id) {
        if (id == null) {
            throw new RuntimeException("Invalid parameter ID");
        }
        repository.deleteById(id);
    }

    public Sort createSort(Object orderBy) {
        return Sort.unsorted();
    }

    public List<Predicate> createPredicates(Object where, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
