package io.xmeta.admin.service;

import io.xmeta.admin.domain.BaseEntity;
import io.xmeta.graphql.model.SortOrder;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.beans.PropertyDescriptor;
import java.io.Serializable;

public abstract class BaseService<R extends JpaRepository<T, ID>, T extends BaseEntity, ID extends Serializable>
        implements CrudService<T, ID> {

    private final R repository;

    @PersistenceContext
    private EntityManager em;

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
        if (orderBy == null) return Sort.unsorted();
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(orderBy);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String name = descriptor.getName();
            Object propertyValue = null;
            try {
                propertyValue = PropertyUtils.getProperty(orderBy, name);
            } catch (Exception ex) {
            }
            if (propertyValue instanceof SortOrder) {
                SortOrder order = (SortOrder) propertyValue;
                if (order == SortOrder.Asc) {
                    return Sort.by(Sort.Order.asc(name));
                } else {
                    return Sort.by(Sort.Order.desc(name));
                }
            }
        }
        return Sort.unsorted();
    }

    public void clear() {
        this.em.clear();
    }
}
