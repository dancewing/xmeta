package io.xmeta.graphql.constants;

import io.xmeta.graphql.util.Maps;

import java.util.Arrays;
import java.util.Map;

public abstract class FieldConstProperties {
    public static Map<String, Object> SingleLineText = Maps.of("maxLength", 1000).build();
    public static Map<String, Object> MultiLineText = Maps.of("maxLength", 1000).build();
    public static Map<String, Object> WholeNumber = Maps.of("minimumValue", -999999999).and("maximumValue", 999999999).build();
    public static Map<String, Object> DecimalNumber = Maps.of("minimumValue", -999999999).and("maximumValue",
            999999999).and("precision", 2).build();
    public static Map<String, Object> DateTime = Maps.of("timeZone", "localTime").and("dateOnly", false).build();
    public static Map<String, Object> Lookup =
            Maps.of("relatedEntityId", "").and("allowMultipleSelection", false).and("relatedFieldId", "").build();
    public static Map<String, Object> OptionSet =
            Maps.of("options", Arrays.asList(Maps.of("label", "Option 1").and("value", "Option1").build())).build();

    public static Map<String, Object> MultiSelectOptionSet =
            Maps.of("options", Arrays.asList(Maps.of("label", "Option 1").and("value", "Option1").build())).build();
}
