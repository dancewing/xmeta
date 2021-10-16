package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.List;

public interface MetaCacheLoaderService extends MetaLoaderService {

    Entity getEntity(String entityId);
    boolean hasValue();
}
