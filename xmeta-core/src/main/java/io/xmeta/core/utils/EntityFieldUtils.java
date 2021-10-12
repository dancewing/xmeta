package io.xmeta.core.utils;

import io.xmeta.core.domain.DataType;
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
}
