package io.xmeta.core.utils;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.RelationType;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * EntityField 辅助类
 */
public final class EntityFieldUtils {

    /***
     * 将数据类型转成java类型
     * @param dataType
     * @param properties
     * @return
     */
    public static String determineJavaType(String dataType, Map<String, Object> properties) {
        Assert.hasText(dataType, "data must not be empty");
        DataType type = DataType.valueOf(dataType);

        switch (type) {
            case Id:
            case SingleLineText:
            case MultiLineText:
                return "java.lang.String";
            case WholeNumber:
                return "java.lang.Integer";
            case DecimalNumber:
                return "java.math.BigDecimal";
            case CreatedAt:
            case UpdatedAt:
            case DateTime:
                return "java.time.LocalDateTime";
            default:
                return null;
        }
    }

    public static RelationType getRelationType(Map<String, Object> properties) {
        String relationType = MapUtils.getString(properties, "relationType");
        if (StringUtils.isNotEmpty(relationType)) {
            return RelationType.valueOf(relationType);
        }
        return null;
    }

    public static RelationType getTargetRelationType(RelationType relationType) {
        switch (relationType) {
            case manyToOne:
                return RelationType.oneToMany;
            case oneToMany:
                return RelationType.manyToOne;
            case manyToMany:
                return RelationType.manyToMany;
            default:
                throw new RuntimeException("un-support relation type convert");
        }
    }

    public static boolean allowMultipleSelection(RelationType relationType) {
        Assert.notNull(relationType, "relationType can not be null");
        return (relationType == RelationType.manyToOne || relationType == RelationType.manyToMany);
    }
}
