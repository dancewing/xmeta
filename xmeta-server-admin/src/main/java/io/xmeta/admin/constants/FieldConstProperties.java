package io.xmeta.admin.constants;

import io.xmeta.admin.util.Maps;
import io.xmeta.core.domain.RelationType;
import io.xmeta.core.domain.RelationTypeConstants;

import java.util.Arrays;
import java.util.Map;

public abstract class FieldConstProperties {
    public static Map<String, Object> Id = Maps.of("maxLength", 64).build();
    public static Map<String, Object> SingleLineText = Maps.of("maxLength", 1000).build();
    public static Map<String, Object> MultiLineText = Maps.of("maxLength", 1000).build();
    public static Map<String, Object> WholeNumber = Maps.of("minimumValue", -999999999).and("maximumValue", 999999999).build();
    public static Map<String, Object> DecimalNumber = Maps.of("minimumValue", -999999999).and("maximumValue",
            999999999).and("precision", 2).build();
    public static Map<String, Object> DateTime = Maps.of("timeZone", "localTime").and("dateOnly", false).build();
    public static Map<String, Object> Lookup =
            Maps.of(RelationTypeConstants.RELATED_ENTITY_ID, "").and(RelationTypeConstants.RELATION_TYPE, RelationType.ManyToOne.name()).and(RelationTypeConstants.RELATED_FIELD_ID, "").build();
    public static Map<String, Object> OptionSet =
            Maps.of("options", Arrays.asList(Maps.of("label", "Option 1").and("value", "Option1").build())).build();

    public static Map<String, Object> MultiSelectOptionSet =
            Maps.of("options", Arrays.asList(Maps.of("label", "Option 1").and("value", "Option1").build())).build();
}
