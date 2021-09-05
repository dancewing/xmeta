package io.xmeta.graphql.mapper;

import org.hibernate.collection.spi.PersistentCollection;
import org.mapstruct.BeforeMapping;
import org.mapstruct.TargetType;

import java.time.ZonedDateTime;
import java.util.*;

public interface BaseMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);

    default Date toDate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime!=null){
            return Date.from(zonedDateTime.toInstant());
        }
        return null;
    }

    @BeforeMapping
    default <T> Set<T> fixLazyLoadingSet(Collection<?> c, @TargetType Class<?> targetType) {
        if (!Util.wasInitialized(c)) {
            return Collections.emptySet();
        }
        return null;
    }

    @BeforeMapping
    default <T> List<T> fixLazyLoadingList(Collection<?> c, @TargetType Class<?> targetType) {
        if (!Util.wasInitialized(c)) {
            return Collections.emptyList();
        }
        return null;
    }

    class Util {
        static boolean wasInitialized(Object c) {
            if (!(c instanceof PersistentCollection)) {
                return true;
            }

            PersistentCollection pc = (PersistentCollection) c;
            return pc.wasInitialized();
        }
    }
}
