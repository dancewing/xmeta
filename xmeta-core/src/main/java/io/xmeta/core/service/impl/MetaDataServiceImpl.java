package io.xmeta.core.service.impl;

import io.xmeta.core.MetaException;
import io.xmeta.core.dao.EntityJdbcTemplate;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.core.sqlbuilder.MetaRuntime;
import io.xmeta.core.sqlbuilder.SQLCreator;
import io.xmeta.core.sqlbuilder.converter.ConverterFactory;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.EntityUtils;
import io.xmeta.core.utils.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jdbc.core.convert.Identifier;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
public class MetaDataServiceImpl implements MetaDataService {

    private final MetaRuntime metaRuntime;
    private final MetaEntityService metaEntityService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ConverterFactory converterFactory;
    private final EntityJdbcTemplate entityJdbcTemplate;

    public MetaDataServiceImpl(MetaRuntime metaRuntime, MetaEntityService metaEntityService, NamedParameterJdbcTemplate namedParameterJdbcTemplate, ConverterFactory converterFactory, EntityJdbcTemplate entityDataAccessStrategy) {
        this.metaRuntime = metaRuntime;
        this.metaEntityService = metaEntityService;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.converterFactory = converterFactory;
        this.entityJdbcTemplate = entityDataAccessStrategy;
    }

    public List<Map<String, Object>> query(String entityId, Integer page, Integer size) {
        Entity entity = this.metaEntityService.getEntity(entityId);
        Assert.notNull(entity, "未知的entityId");

        return this.entityJdbcTemplate.findAll(entity);
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
/*
        Object pk = data.remove(id.getName());

        Map<String, Object> toSaved = convertParamsToEntityData(entity, data, false);

        UpdateCreator updateCreator = new UpdateCreator(entity);

        toSaved.forEach(updateCreator::setValue);

        updateCreator.whereEquals(id.getName(), pk);

        log(updateCreator.createSqlParameterSource(), updateCreator.getSQL());

        int result = this.namedParameterJdbcTemplate.update(updateCreator.getSQL(), updateCreator.createSqlParameterSource());
        if (result != 1) {
            throw new MetaException("can't update value");
        }*/
        return null;
    }

    private Map<String, Object> convertParamsToEntityData(Entity entity, Map<String, Object> data, boolean create) {
        Map<String, Object> toSaved = new HashMap<>();

        EntityUtils.doWithAll(entity, field -> {
            DataType dataType = field.getDataType();

            if (DataType.CreatedAt == dataType && create) {
                toSaved.put(field.getName(), LocalDateTime.now());
            } else if (DataType.UpdatedAt == dataType) {
                toSaved.put(field.getName(), LocalDateTime.now());
            }

            if (data.containsKey(field.getName())) {
                if (DataType.CreatedAt == dataType && create) {
                    toSaved.put(field.getName(), LocalDateTime.now());
                } else if (DataType.UpdatedAt == dataType) {
                    toSaved.put(field.getName(), LocalDateTime.now());
                } else if (DataType.Lookup == dataType) {

                } else {
                    Object value = EntityFieldUtils.convertParamValue(dataType, data.get(field.getName()));
                    toSaved.put(field.getName(), value);
                }
            } else {
//                if (entityField.getRequired() && !EntityFieldUtils.isSystemControl(entityField.getDataType())) {
//                    if (!create && entityField.getDataType() != DataType.Id) {
//                        throw new MetaException("必填字段" + entityField.getName() + "缺失");
//                    }
//                }
            }

        });

        return toSaved;
    }

    @Transactional
    public int delete(String entityId, Map<String, Object> data) {

        Entity entity = this.metaEntityService.getEntity(entityId);

        if (MapUtils.isEmpty(data)) {
            throw new MetaException("缺少删除条件");
        }

        Map<String, Object> toDeleted = convertParamsToEntityData(entity, data, false);

/*        DeleteCreator deleteCreator = new DeleteCreator(entity.getTable());

        toDeleted.forEach(deleteCreator::whereEquals);

        log(deleteCreator.createSqlParameterSource(), deleteCreator.getSQL());*/

        // return this.namedParameterJdbcTemplate.update(deleteCreator.getSQL(), deleteCreator.createSqlParameterSource());
        return 0;
    }

    @Transactional
    public int deleteAll(String entityId) {
        return 0;
    }

    private void log(SQLCreator sqlCreator) {
        if (metaRuntime.isShowSQL()) {
            log.info(sqlCreator.getSQL());
            String[] parameterNames = sqlCreator.createSqlParameterSource().getParameterNames();
            List<String> params = new ArrayList<>();
            for (String param: parameterNames) {
                params.add(param + ":" + sqlCreator.createSqlParameterSource().getValue(param));
            }
            log.info(StringUtils.join(params, ","));
        }
    }
}
