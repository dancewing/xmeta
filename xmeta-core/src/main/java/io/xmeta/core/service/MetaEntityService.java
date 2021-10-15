package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

/**
 * 对数据模型的操作修改
 */
public interface MetaEntityService {

    Entity getEntity(String entityId);
}
