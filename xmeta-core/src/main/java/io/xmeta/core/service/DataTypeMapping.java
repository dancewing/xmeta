package io.xmeta.core.service;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.exception.UnknownDataTypeMappingException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DataTypeMapping {

    private static final Map<DataType, Class<?>> dataTypeMappings = new HashMap<>();

    static {
        dataTypeMappings.put(DataType.Id, String.class);
        dataTypeMappings.put(DataType.SingleLineText, String.class);
        dataTypeMappings.put(DataType.MultiLineText, String.class);
        dataTypeMappings.put(DataType.WholeNumber, Integer.class);
        dataTypeMappings.put(DataType.DecimalNumber, BigDecimal.class);
        dataTypeMappings.put(DataType.Date, LocalDate.class);
        dataTypeMappings.put(DataType.Time, LocalTime.class);
        dataTypeMappings.put(DataType.DateTime, LocalDateTime.class);

        // dataTypeMappings.put(DataType.Lookup, LocalDateTime.class);

        dataTypeMappings.put(DataType.MultiSelectOptionSet, String.class);
        dataTypeMappings.put(DataType.OptionSet, String.class);
        dataTypeMappings.put(DataType.GeographicLocation, String.class);

        dataTypeMappings.put(DataType.CreatedAt, LocalDateTime.class);
        dataTypeMappings.put(DataType.UpdatedAt, LocalDateTime.class);
        dataTypeMappings.put(DataType.CreatedBy, String.class);
        dataTypeMappings.put(DataType.UpdatedBy, String.class);

        dataTypeMappings.put(DataType.Email, String.class);
        dataTypeMappings.put(DataType.Roles, String.class);
        dataTypeMappings.put(DataType.Username, String.class);
        dataTypeMappings.put(DataType.Password, String.class);
        dataTypeMappings.put(DataType.Json, String.class);
    }

    public Class<?> javaTypeFor(DataType type) {
        Assert.notNull(type, "Type must not be null.");

        Optional<Class<?>> optionalClass = dataTypeMappings.keySet().stream() //
                .filter(dataType -> dataType == type) //
                .findFirst() //
                .map(dataTypeMappings::get);
        if (optionalClass.isPresent()) {
            return optionalClass.get();
        } else {
            throw new UnknownDataTypeMappingException("DataType: " + type + " can't find mapping java type");
        }
    }

    public Class<?> javaTypeFor(String typeName) {
        Assert.notNull(typeName, "Type must not be null.");
        DataType type = DataType.valueOf(typeName);
        if (type == null) {
            throw new UnknownDataTypeMappingException();
        }

        return javaTypeFor(type);
    }
}
