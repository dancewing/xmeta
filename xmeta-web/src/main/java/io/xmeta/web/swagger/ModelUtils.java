package io.xmeta.web.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedArrayType;
import com.fasterxml.classmate.types.ResolvedPrimitiveType;
import springfox.documentation.schema.Maps;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static io.xmeta.web.swagger.Types.typeNameFor;


public class ModelUtils {

    public static String simpleQualifiedTypeName(ResolvedType type) {
        if (type instanceof ResolvedPrimitiveType) {
            Type primitiveType = type.getErasedType();
            return typeNameFor(primitiveType);
        }
        if (type instanceof ResolvedArrayType) {
            return typeNameFor(type.getArrayElementType().getErasedType());
        }

        return type.getErasedType().getName();
    }

    public static ResolvedType collectionElementType(ResolvedType type) {
        if (List.class.isAssignableFrom(type.getErasedType())) {
            return elementType(type, List.class);
        } else if (Set.class.isAssignableFrom(type.getErasedType())) {
            return elementType(type, Set.class);
        } else if (type.isArray()) {
            return type.getArrayElementType();
        } else if ((Collection.class.isAssignableFrom(type.getErasedType()) && !Maps.isMapType(type))) {
            return elementType(type, Collection.class);
        } else {
            return null;
        }
    }

    public static boolean isContainerType(ResolvedType type) {
        return List.class.isAssignableFrom(type.getErasedType()) ||
                Set.class.isAssignableFrom(type.getErasedType()) ||
                (Collection.class.isAssignableFrom(type.getErasedType()) && !Maps.isMapType(type)) ||
                type.isArray();
    }

    public static String containerType(ResolvedType type) {
        if (List.class.isAssignableFrom(type.getErasedType())) {
            return "List";
        } else if (Set.class.isAssignableFrom(type.getErasedType())) {
            return "Set";
        } else if (type.isArray()) {
            return "Array";
        } else if (Collection.class.isAssignableFrom(type.getErasedType()) && !Maps.isMapType(type)) {
            return "List";
        } else {
            throw new UnsupportedOperationException(String.format("Type is not collection type %s", type));
        }
    }

    private static <T extends Collection> ResolvedType elementType(ResolvedType container, Class<T> collectionType) {
        List<ResolvedType> resolvedTypes = container.typeParametersFor(collectionType);
        if (resolvedTypes.size() == 1) {
            return resolvedTypes.get(0);
        }
        return new TypeResolver().resolve(Object.class);
    }
}
