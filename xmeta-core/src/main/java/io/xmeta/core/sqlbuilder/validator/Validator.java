package io.xmeta.core.sqlbuilder.validator;

import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;

import java.util.Map;

public interface Validator {
    void process(Entity entity, EntityField field, Map<String, Object> data, boolean ignorePrivate);
}
