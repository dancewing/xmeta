package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.List;

/**
 * 对数据模型的查询操作
 */
public interface MetaEntityService {

    List<Entity> load();

    Entity getEntity(String entityId);

}
