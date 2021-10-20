package io.xmeta.core.service;

import java.util.List;
import java.util.Map;

/***
 * 对数据模型的增删改查操作，在EntityJdbcTemplate操作前，
 * 做一次封装，完成字段效验、系统字段自动补全等操作
 */
public interface MetaDataService {

    /**
     * 简单的查询服务， 可分页查，但结果只出列表
     *
     * @param entityId
     * @param page
     * @param size
     * @return
     */
    List<Map<String, Object>> query(String entityId, Integer page, Integer size);

    Map<String, Object> save(String entityId, Map<String, Object> data);

    Map<String, Object> update(String entityId, Map<String, Object> data);

    int deleteById(String entityId, Map<String, Object> data);

    int deleteAll(String entityId);
}
