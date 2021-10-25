package io.xmeta.core.utils;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.domain.RelationType;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

/**
 * EntityField 辅助类
 */
public final class EntityFieldUtils {

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

    public static boolean allowMultipleSelection(RelationType relationType) {
        Assert.notNull(relationType, "relationType can not be null");
        return (relationType == RelationType.OneToMany || relationType == RelationType.ManyToMany);
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
        return (DataType.CreatedAt == dataType || DataType.UpdatedAt == dataType);
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
}
