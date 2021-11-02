package io.xmeta.core.domain;

import io.xmeta.core.utils.Maps;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Map;

public enum DataType {

    Id("Id"){
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("maxLength", 64).build();
        }
    },
    SingleLineText("SingleLineText") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("maxLength", 1000).build();
        }
    },
    MultiLineText("MultiLineText") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("maxLength", 2000).build();
        }
    },
    WholeNumber("WholeNumber") {
        @Override
        public Class<?> getJavaType() {
            return Integer.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("minimumValue", -999999999).and("maximumValue", 999999999).build();
        }
    },
    DecimalNumber("DecimalNumber") {
        @Override
        public Class<?> getJavaType() {
            return BigDecimal.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("minimumValue", -999999999).and("maximumValue",
                    999999999).and("precision", 2).build();
        }
    },
    Date("Date") {
        @Override
        public Class<?> getJavaType() {
            return LocalDate.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    Time("Time") {
        @Override
        public Class<?> getJavaType() {
            return LocalTime.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    DateTime("DateTime"){
        @Override
        public Class<?> getJavaType() {
            return LocalDateTime.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("timeZone", "localTime").and("dateOnly", false).build();
        }
    },
    Boolean("Boolean"){
        @Override
        public Class<?> getJavaType() {
            return Boolean.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    Lookup("Lookup") {
        @Override
        public Class<?> getJavaType() {
            return null;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of(RelationTypeConstants.RELATED_ENTITY_ID, "")
                    .and(RelationTypeConstants.RELATION_TYPE, RelationType.ManyToOne.name())
                    .and(RelationTypeConstants.RELATED_FIELD_ID, "").build();
        }
    },
    Email("Email") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    MultiSelectOptionSet("MultiSelectOptionSet") {
        @Override
        public Class<?> getJavaType() {
            return Class.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("options", Collections.singletonList(Maps.of("label", "Option 1").and("value", "Option1").build())).build();
        }
    },
    OptionSet("OptionSet") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.of("options", Collections.singletonList(Maps.of("label", "Option 1").and("value", "Option1").build())).build();
        }
    },
    GeographicLocation("GeographicLocation") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    CreatedAt("CreatedAt") {
        @Override
        public Class<?> getJavaType() {
            return LocalDateTime.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    UpdatedAt("UpdatedAt") {
        @Override
        public Class<?> getJavaType() {
            return LocalDateTime.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    CreatedBy("CreatedBy") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    UpdatedBy("UpdatedBy") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    Roles("Roles") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    Username("Username") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    Password("Password") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    },
    Json("Json") {
        @Override
        public Class<?> getJavaType() {
            return String.class;
        }

        @Override
        public Map<String, Object> getProperties() {
            return Maps.empty();
        }
    };

    private final String name;

    DataType(String name) {
        this.name = name;
    }

    /**
     * java 类型
     * @return
     */
    public Class<?> getJavaType() {return null;}

    /**
     * 默认的属性
     * @return
     */
    public Map<String, Object> getProperties() {
        return Maps.empty();
    }


    public static DataType fromName(String name) {
        for (DataType dataType: values()) {
            if (StringUtils.equals(name, dataType.name)) {
                return dataType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
