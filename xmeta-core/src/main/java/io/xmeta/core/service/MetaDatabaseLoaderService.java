package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.List;

public interface MetaDatabaseLoaderService extends MetaLoaderService {

    void save(List<Entity> entities);

    long getLastSyncTime();
}
