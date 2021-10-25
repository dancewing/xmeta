package io.xmeta.screw.model;

import io.xmeta.core.dialect.MetaDialect;
import io.xmeta.screw.util.CaseInsensitiveMap;

import java.util.Collections;
import java.util.List;

public interface ScriptModel {
    default String sqlCreate(MetaDialect dialect, String defaultCatalog, String defaultSchema) {
        return "";
    }

    default String sqlDrop(MetaDialect dialect, String defaultCatalog, String defaultSchema) {
        return "";
    }

    default List<String> sqlAlters(MetaDialect dialect, CaseInsensitiveMap<TableColumn> columnMap, String defaultCatalog, String defaultSchema) {
        return Collections.emptyList();
    }
}
