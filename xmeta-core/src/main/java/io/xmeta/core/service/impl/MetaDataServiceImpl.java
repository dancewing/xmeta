package io.xmeta.core.service.impl;

import io.xmeta.core.dao.EntityJdbcTemplate;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.exception.MetaException;
import io.xmeta.core.service.FieldConversionService;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.EntityUtils;
import io.xmeta.core.utils.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.convert.Identifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional(readOnly = true)
public class MetaDataServiceImpl implements MetaDataService {

    private final MetaEntityService metaEntityService;
    private final EntityJdbcTemplate entityJdbcTemplate;
    private final FieldConversionService fieldConversionService;

    public MetaDataServiceImpl(MetaEntityService metaEntityService, EntityJdbcTemplate entityDataAccessStrategy, FieldConversionService fieldConversionService) {
        this.metaEntityService = metaEntityService;
        this.entityJdbcTemplate = entityDataAccessStrategy;
        this.fieldConversionService = fieldConversionService;
    }

    public List<Map<String, Object>> query(String entityId, Integer page, Integer size) {
        Entity entity = this.metaEntityService.getEntity(entityId);
        Assert.notNull(entity, "未知的entityId");

        List<Map<String, Object>> result;
        if (page == null || size == null) {
            result = this.entityJdbcTemplate.findAll(entity);
        } else {
            if (page == null) {
                page = 1;
            }
            if (size == null) {
                size = 20;
            }
            Pageable pageable = PageRequest.of(page - 1, size);
            result = this.entityJdbcTemplate.findAll(entity, pageable);
        }

        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> save(String entityId, Map<String, Object> data) {
        Entity entity = this.metaEntityService.getEntity(entityId);

        EntityField id = EntityFieldUtils.findPK(entity).orElseThrow(MetaException::new);

        if (!data.containsKey(id.getName())) {
            data.put(id.getName(), IDGenerator.nextId());
        }

        Map<String, Object> toSaved = convertParamsToEntityData(entity, data, true);

        entityJdbcTemplate.insert(entity, toSaved, Identifier.empty());

        return toSaved;
    }

    @Transactional
    public Map<String, Object> update(String entityId, Map<String, Object> data) {
        Entity entity = this.metaEntityService.getEntity(entityId);

        EntityField id = EntityFieldUtils.findPK(entity).orElseThrow(MetaException::new);

        if (!data.containsKey(id.getName())) {
            throw new MetaException("缺少主键字段:" + id.getName());
        }

        Map<String, Object> convertData = convertParamsToEntityData(entity, data, true);
        Map<String, Object> toUpdated = new HashMap<>(convertData);
        Map<String, Object> where = new HashMap<>();
        where.put(id.getName(), toUpdated.remove(id.getName()));
        int result = entityJdbcTemplate.update(entity, toUpdated, where);
        if (result != 1) {
            throw new MetaException("can't update value");
        }
        return convertData;
    }

    private Map<String, Object> convertParamsToEntityData(Entity entity, Map<String, Object> data, boolean create) {
        Map<String, Object> toSaved = new HashMap<>();

        EntityUtils.doWithAll(entity, field -> {
            DataType dataType = field.getDataType();

            if (DataType.CreatedAt == dataType && create) {
                toSaved.put(field.getName(), LocalDateTime.now());
                return;
            }
            if (DataType.UpdatedAt == dataType) {
                toSaved.put(field.getName(), LocalDateTime.now());
                return;
            }

            if (data.containsKey(field.getName())) {
                // 特殊字段处理
                if (DataType.Lookup == dataType) {

                } else {
                    Object value = fieldConversionService.convert(field, data.get(field.getName()));
                    toSaved.put(field.getName(), value);
                }
            } else {

            }

        });

        return toSaved;
    }

    @Transactional
    public int deleteById(String entityId, Map<String, Object> value) {

        Entity entity = this.metaEntityService.getEntity(entityId);
        EntityField id = EntityFieldUtils.findPK(entity).orElseThrow(MetaException::new);

        if (!value.containsKey(id.getName())) {
            throw new MetaException("缺少主键字段:" + id.getName());
        }

        Object pkValue = value.get(id.getName());

        return this.entityJdbcTemplate.deleteById(entity, pkValue);
    }

    @Transactional
    public int deleteAll(String entityId) {
        return 0;
    }
}
