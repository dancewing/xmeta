package io.xmeta.core.service.impl;

import io.xmeta.core.MetaException;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.EntityCacheService;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.core.service.MetaLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaEntityServiceImpl implements MetaEntityService {

    private EntityCacheService entityCacheService;

    private MetaLoaderService metaLoaderService;

    @Autowired
    public void setEntityCacheService(EntityCacheService entityCacheService) {
        this.entityCacheService = entityCacheService;
    }

    @Autowired(required = false)
    public void setMetaLoaderService(MetaLoaderService metaLoaderService) {
        this.metaLoaderService = metaLoaderService;
    }

    @Override
    public Entity getEntity(String entityId) {
        if (this.metaLoaderService == null) {
            throw new MetaException("can't get metaLoaderService");
        }

        if (!this.entityCacheService.hasValue()) {
            List<Entity> entities = this.metaLoaderService.load();
            this.entityCacheService.put(entities);
            return this.entityCacheService.getEntity(entityId);
        } else {
            return this.entityCacheService.getEntity(entityId);
        }
    }
}
