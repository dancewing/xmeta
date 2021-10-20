package io.xmeta.core.utils;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.domain.RelationType;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

/**
 * EntityField 辅助类
 */
public final class EntityFieldUtils {

    public static Object convertParamValue(DataType dataType, Object value) {
        switch (dataType) {
            case Id:
            case SingleLineText:
            case MultiLineText:
                return objectToString(value);
            case WholeNumber:
                return objectToInteger(value);
            case DecimalNumber:
                return objectToBigDecimal(value);
            case Boolean:
                return objectToBoolean(value);
            case CreatedAt:
            case UpdatedAt:
                break;
            case DateTime:
                return objectToDateTime(value, "yyyy-MM-dd HH:mm:ss");
        }
        return null;
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

    public static Optional<EntityField> findPK(Entity entity) {
        return entity.getFields().stream().filter(entityField -> DataType.Id == entityField.getDataType()).findFirst();
    }

    public static String objectToString(Object o) {
        if (o == null) {
            return "";
        }
        String v = String.valueOf(o);
        if (StringUtils.equalsIgnoreCase("null", v) || StringUtils.equalsIgnoreCase("undefined", v)) {
            return "";
        }
        return v;
    }

    public static Boolean objectToBoolean(Object o) {
        if (o == null) {
            return Boolean.FALSE;
        }
        String v = String.valueOf(o);
        if (StringUtils.equalsIgnoreCase("true", v) || StringUtils.equalsIgnoreCase("Y", v) || StringUtils.equals("1", v)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Integer objectToInteger(Object o) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    public static Long objectToLong(Object o) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    public static BigInteger objectToBigInteger(Object o) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return new BigInteger(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    public static Float objectToFloat(Object o) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return Float.parseFloat(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    public static BigDecimal objectToBigDecimal(Object o) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return new BigDecimal(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    public static LocalDate objectToDate(Object o, String format) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return LocalDate.parse(str, DateTimeFormatter.ofPattern(format));
        } catch (final DateTimeParseException nfe) {
            return null;
        }
    }

    public static LocalDateTime objectToDateTime(Object o, String format) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
        } catch (final DateTimeParseException nfe) {
            return null;
        }
    }

    public static LocalTime objectToTime(Object o, String format) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return LocalTime.parse(str, DateTimeFormatter.ofPattern(format));
        } catch (final DateTimeParseException nfe) {
            return null;
        }
    }

    public static Double objectToDouble(Object o) {
        if (o == null) {
            return null;
        }
        String str = String.valueOf(o);
        try {
            return Double.parseDouble(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    /***
     * 判断字段类型是否系统控制赋值
     * @param dataType
     * @return
     */
    public static boolean isSystemControl(DataType dataType) {
        return (DataType.CreatedAt == dataType || DataType.UpdatedAt == dataType);
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
