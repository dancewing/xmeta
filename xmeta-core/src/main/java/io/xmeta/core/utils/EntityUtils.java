package io.xmeta.core.utils;

import io.xmeta.core.domain.Entity;
import org.springframework.util.Assert;

public class EntityUtils {
    public static void doWithAll(Entity entity, EntityFieldHandler handler) {

        Assert.notNull(handler, "PropertyHandler must not be null!");

        entity.getFields().forEach(field -> {
            handler.doWithField(field);
        });
    }
}
