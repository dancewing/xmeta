package io.xmeta.core.service.impl;

import io.xmeta.core.MetaException;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.MetaCacheLoaderService;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.core.service.MetaDatabaseLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaEntityServiceImpl implements MetaEntityService {

    private MetaCacheLoaderService metaCacheLoaderService;

    private MetaDatabaseLoaderService metaDatabaseLoaderService;

    @Autowired
    public void setEntityCacheService(MetaCacheLoaderService metaCacheLoaderService) {
        this.metaCacheLoaderService = metaCacheLoaderService;
    }

    @Autowired(required = false)
    public void setMetaDatabaseLoaderService(MetaDatabaseLoaderService metaDatabaseLoaderService) {
        this.metaDatabaseLoaderService = metaDatabaseLoaderService;
    }

    @Override
    public List<Entity> load() {
        if (this.metaDatabaseLoaderService == null) {
            throw new MetaException("can't get metaLoaderService");
        }
        List<Entity> entities = this.metaCacheLoaderService.load();
        if (entities.size() > 0) {
            return entities;
        }
        entities = this.metaDatabaseLoaderService.load();
        return entities;
    }

    @Override
    public Entity getEntity(String entityId) {
        if (this.metaDatabaseLoaderService == null) {
            throw new MetaException("can't get metaLoaderService");
        }

        if (!this.metaCacheLoaderService.hasValue()) {
            List<Entity> entities = this.metaDatabaseLoaderService.load();
            this.metaCacheLoaderService.save(entities);
            return this.metaCacheLoaderService.getEntity(entityId);
        } else {
            return this.metaCacheLoaderService.getEntity(entityId);
        }
    }
}
