package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.List;

/***
 *
 */
interface MetaLoaderService {
    List<Entity> load();

    void save(List<Entity> entities);
}
