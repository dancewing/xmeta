package io.xmeta.core.service;

import java.util.List;
import java.util.Map;

public interface MetaDataService {

    /**
     * 简单的查询服务， 可分页查，但结果只出列表
     * @param entityId
     * @param page
     * @param size
     * @return
     */
    List<Map<String, Object>> query(String entityId, Integer page, Integer size);

    Map<String, Object> save(String entityId, Map<String, Object> data);
    Map<String, Object> update(String entityId, Map<String, Object> data);
    int delete(String entityId, Map<String, Object> data);
    int deleteAll(String entityId);
}
