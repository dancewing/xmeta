package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

public interface MetaCacheLoaderService extends MetaLoaderService {

    Entity getEntity(String entityId);

    boolean hasValue();
}
