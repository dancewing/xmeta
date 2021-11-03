package io.xmeta.core.utils;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.domain.RelationType;
import io.xmeta.core.exception.MetaException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EntityField 辅助类
 */
public final class EntityFieldUtils {

    private static final Map<String, Class> CLASS_CACHE = new ConcurrentHashMap<>();


    public static RelationType getRelationType(Map<String, Object> properties) {
        String relationType = MapUtils.getString(properties, "relationType");
        if (StringUtils.isNotEmpty(relationType)) {
            return RelationType.valueOf(relationType);
        }
        return null;
    }

    public static RelationType getTargetRelationType(RelationType relationType) {
        switch (relationType) {
            case ManyToOne:
                return RelationType.OneToMany;
            case OneToMany:
                return RelationType.ManyToOne;
            case ManyToMany:
                return RelationType.ManyToMany;
            default:
                throw new RuntimeException("un-support relation type convert");
        }
    }

    public static Optional<EntityField> findPK(Entity entity) {
        return entity.getFields().stream().filter(entityField -> DataType.Id == entityField.getDataType()).findFirst();
    }

    /***
     * 判断字段类型是否系统控制赋值
     * @param dataType
     * @return
     */
    public static boolean isSystemControl(DataType dataType) {
        return (DataType.CreatedAt == dataType || DataType.UpdatedAt == dataType || DataType.Id == dataType);
    }

    public static boolean isControlledJavaType(DataType dataType) {
        return (DataType.Lookup != dataType);
    }

    /**
     * 判断字段是否可供查询
     *
     * @param field
     * @return
     */
    public static boolean isSelectable(EntityField field) {
        DataType dataType = field.getDataType();
        return (DataType.Lookup != dataType);
    }

    @NonNull
    public static Class<?> getClass(String className) {
        if (CLASS_CACHE.containsKey(className)) {
            return CLASS_CACHE.get(className);
        }
        Class<?> cls = null;
        try {
            cls = ClassUtils.getClass(className, true);
            CLASS_CACHE.put(className, cls);
        } catch (ClassNotFoundException e) {
            throw new MetaException("不能初始化：" + className);
        }
        return cls;
    }
}
