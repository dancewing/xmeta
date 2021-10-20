package io.xmeta.core.service.impl;

import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.MetaCacheLoaderService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class RedisMetaEntityCacheService implements MetaCacheLoaderService {

    static String ENTITY_REDIS_CACHE_KEY = "ENTITY_REDIS_CACHE_KEY";

    @Resource
    private RedisTemplate<String, Entity> redisTemplate;

    @Override
    public List<Entity> load() {
        HashOperations<String, String, Entity> hashOperations = this.redisTemplate.opsForHash();
        Map<String, Entity> entityMap = hashOperations.entries(ENTITY_REDIS_CACHE_KEY);
        return new ArrayList<>(entityMap.values());
    }

    @Override
    public boolean hasValue() {
        Boolean hs = this.redisTemplate.hasKey(ENTITY_REDIS_CACHE_KEY);
        return hs != null && hs;
    }

    @Override
    public void save(List<Entity> entities) {
        Assert.notNull(entities, "entities, can not be null");
        //clear current
        this.redisTemplate.delete(ENTITY_REDIS_CACHE_KEY);

        Map<String, Entity> entityMap = new HashMap<>();
        entities.forEach(entity -> {
            entityMap.put(entity.getId(), entity);
        });
        this.redisTemplate.opsForHash().putAll(ENTITY_REDIS_CACHE_KEY, entityMap);
    }

    @Override
    public Entity getEntity(String entityId) {
        HashOperations<String, String, Entity> hashOperations = this.redisTemplate.opsForHash();
        return hashOperations.get(ENTITY_REDIS_CACHE_KEY, entityId);
    }
}
