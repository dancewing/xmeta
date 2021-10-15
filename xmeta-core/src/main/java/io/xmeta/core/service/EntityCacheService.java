package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.List;

public interface EntityCacheService {
    List<Entity> load();
    void put(List<Entity> entities);
    Entity getEntity(String entityId);
    boolean hasValue();
}
