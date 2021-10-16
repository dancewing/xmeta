package io.xmeta.core.service;

import java.util.Map;

public interface MetaDataService {
    Map<String, Object> save(String entityId, Map<String, Object> data);
    Map<String, Object> update(String entityId, Map<String, Object> data);
    int delete(String entityId, Map<String, Object> data);
    int deleteAll(String entityId);
}
