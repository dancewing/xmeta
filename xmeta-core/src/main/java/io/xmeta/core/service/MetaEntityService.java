package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.List;
import java.util.Map;

/**
 * 对数据模型的查询操作
 */
public interface MetaEntityService {

    List<Entity> load();

    Map<String, Entity> loadAsMap();

    Entity getEntity(String entityId);

}
