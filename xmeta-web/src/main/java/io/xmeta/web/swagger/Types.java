package io.xmeta.web.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.collect.Sets.newHashSet;

public class Types {
  private Types() {
    throw new UnsupportedOperationException();
  }

  private static final Set<String> baseTypes = newHashSet(
      "int",
      "date",
      "string",
      "double",
      "float",
      "boolean",
      "byte",
      "object",
      "long",
      "date-time",
      "__file",
      "biginteger",
      "bigdecimal",
      "uuid");
  private static final Map<Type, String> typeNameLookup = ImmutableMap.<Type, String>builder()
      .put(Long.TYPE, "long")
      .put(Short.TYPE, "int")
      .put(Integer.TYPE, "int")
      .put(Double.TYPE, "double")
      .put(Float.TYPE, "float")
      .put(Byte.TYPE, "byte")
      .put(Boolean.TYPE, "boolean")
      .put(Character.TYPE, "string")

      .put(Date.class, "date-time")
      .put(LocalDateTime.class, "date-time")
      .put(java.sql.Date.class, "date")
      .put(LocalDate.class, "date")
      .put(String.class, "string")
      .put(Object.class, "object")
      .put(Long.class, "long")
      .put(Integer.class, "int")
      .put(Short.class, "int")
      .put(Double.class, "double")
      .put(Float.class, "float")
      .put(Boolean.class, "boolean")
      .put(Byte.class, "byte")
      .put(BigDecimal.class, "bigdecimal")
      .put(BigInteger.class, "biginteger")
      .put(Currency.class, "string")
      .put(UUID.class, "uuid")
      .put(MultipartFile.class, "__file")
      .build();

  public static String typeNameFor(Type type) {
    return typeNameLookup.get(type);
  }

  public static boolean isBaseType(String typeName) {
    return baseTypes.contains(typeName);
  }

  public static boolean isBaseType(ResolvedType type) {
    return baseTypes.contains(typeNameFor(type.getErasedType()));
  }

  public static boolean isVoid(ResolvedType returnType) {
    return Void.class.equals(returnType.getErasedType()) || Void.TYPE.equals(returnType.getErasedType());
  }
}
