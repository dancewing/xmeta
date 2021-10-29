package io.xmeta.core.service;

import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.EntityField;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;

public class FieldConversionService {

    private final ConversionService conversionService;
    private final DataTypeMapping dataTypeMapping;

    public FieldConversionService(ConversionService conversionService, DataTypeMapping dataTypeMapping) {
        this.conversionService = conversionService;
        this.dataTypeMapping = dataTypeMapping;
    }

    @Nullable
    public Object convert(EntityField field, Object sourceValue) {
        if (sourceValue == null) {
            return null;
        }
        DataType dataType = field.getDataType();
        Class<?> cls = dataTypeMapping.javaTypeFor(dataType);
        if (cls.isAssignableFrom(sourceValue.getClass())) {
            return sourceValue;
        }
        return conversionService.convert(sourceValue, cls);
    }

}
