package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.BaseEntity;

public interface CrudService<T extends BaseEntity, ID> {

    T create(T resource);

    T update(ID id, T resource);

    T getById(ID id);

    void delete(ID id);
}
