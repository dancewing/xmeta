package io.xmeta.admin.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xmeta.graphql.model.IBlock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class ObjectMapperUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T extends IBlock> T toBlock(byte[] bytes, Class<T> block) {
        if (bytes != null) {
            try {
                return objectMapper.readValue(bytes, block);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    public static byte[] toBytes(Map<String, Object> value) {
        if (value != null) {
            try {
                return objectMapper.writeValueAsBytes(value);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    public static byte[] toBytes(Object value) {
        if (value != null) {
            try {
                return objectMapper.writeValueAsBytes(value);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    public static Map<String, Object> toMap(byte[] bytes) {
        if (bytes != null) {
            try {
                return objectMapper.readValue(bytes, Map.class);
            } catch (IOException e) {
                log.error(e.getMessage());
                return new HashMap<>();
            }
        }
        return new HashMap<>();
    }

    /**
     * 序列化或发序列化时，生成字段映射列表
     *
     * @param target
     * @param serialize
     * @return
     */
    public static Map<String, String> findFieldMapping(Class target, boolean serialize) {
        Map<String, String> map = new HashMap<>();
        List<Field> fields = FieldUtils.getAllFieldsList(target);
        fields.stream().forEach(field -> {
            map.put(field.getName(), field.getName());
        });
        List<Field> annoFields = FieldUtils.getFieldsListWithAnnotation(target, JsonProperty.class);
        if (annoFields.size() == 0) return map;
        annoFields.forEach(field -> {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (StringUtils.isNotEmpty(jsonProperty.value())) {
                if (serialize) {
                    map.put(field.getName(), jsonProperty.value());
                } else {
                    map.remove(field.getName());
                    map.put(jsonProperty.value(), field.getName());
                }

            }
        });
        return map;
    }
}
