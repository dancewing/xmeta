package io.xmeta.core.service;

import io.xmeta.core.domain.DataType;

import java.util.HashMap;
import java.util.Map;

public class DataTypeMapping {

    /****
     *     SingleLineText("SingleLineText"),
     *     MultiLineText("MultiLineText"),
     *     Email("Email"),
     *     WholeNumber("WholeNumber"),
     *     DateTime("DateTime"),
     *     DecimalNumber("DecimalNumber"),
     *     Lookup("Lookup"),
     *     MultiSelectOptionSet("MultiSelectOptionSet"),
     *     OptionSet("OptionSet"),
     *     Boolean("Boolean"),
     *     GeographicLocation("GeographicLocation"),
     *     CreatedAt("CreatedAt"),
     *     UpdatedAt("UpdatedAt"),
     *     Roles("Roles"),
     *     Username("Username"),
     *     Password("Password"),
     *     Json("Json");
     */
    private static final Map<DataType, Class<?>> dataTypeMappings = new HashMap<>();
    static {
        dataTypeMappings.put(DataType.Id, String.class);
        dataTypeMappings.put(DataType.SingleLineText, String.class);
        dataTypeMappings.put(DataType.MultiLineText, String.class);
        dataTypeMappings.put(DataType.Email, String.class);
        dataTypeMappings.put(DataType.WholeNumber, String.class);
        dataTypeMappings.put(DataType.DecimalNumber, String.class);
        dataTypeMappings.put(DataType.DateTime, String.class);
    }
}
