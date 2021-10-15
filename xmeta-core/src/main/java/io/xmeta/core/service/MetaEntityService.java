package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 对数据模型的操作修改
 */
public interface MetaEntityService {

    Entity getEntity(String entityId);
}
