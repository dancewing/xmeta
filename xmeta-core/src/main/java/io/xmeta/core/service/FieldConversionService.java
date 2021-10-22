package io.xmeta.core.service;

import io.xmeta.core.domain.DataType;
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
    public Object convert(DataType dataType, Object sourceValue) {
        if (sourceValue == null) {
            return null;
        }
        Class<?> cls = dataTypeMapping.javaTypeFor(dataType);
        if (cls.isAssignableFrom(sourceValue.getClass())) {
            return sourceValue;
        }
        return conversionService.convert(sourceValue, cls);
    }

}
