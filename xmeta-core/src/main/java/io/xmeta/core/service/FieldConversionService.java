package io.xmeta.core.service;

import io.xmeta.core.domain.DataType;
import org.springframework.core.convert.ConversionService;

public class FieldConversionService {

    private ConversionService conversionService;
    private DataTypeMapping dataTypeMapping;

    public FieldConversionService(ConversionService conversionService, DataTypeMapping dataTypeMapping) {
        this.conversionService = conversionService;
        this.dataTypeMapping = dataTypeMapping;
    }

    public Object convert(DataType dataType, Object sourceValue) {
        Class<?> cls = dataTypeMapping.javaTypeFor(dataType);
        return conversionService.convert(sourceValue, cls);
    }

}
