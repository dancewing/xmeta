package io.xmeta.core.domain;

import java.util.Map;

public class FieldProperties {

    public static final String STRING_MIN_LENGTH = "minLength";
    public static final String STRING_MAX_LENGTH = "maxLength";

    public static final String NUMBER_MINIMUM_VALUE = "minimumValue";
    public static final String NUMBER_MAXIMUM_VALUE = "maximumValue";
    public static final String NUMBER_PRECISION = "precision";

   private final Map<String, Object> properties;

    public FieldProperties(Map<String, Object> properties) {
        this.properties = properties;
    }




}
