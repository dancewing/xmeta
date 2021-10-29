package io.xmeta.deploy.utils;

import io.xmeta.core.dao.JdbcUtil;
import io.xmeta.screw.model.Database;
import io.xmeta.screw.model.Table;
import io.xmeta.screw.model.TableColumn;

public final class MetaMappingGenernator {

    public static final String XMETA_ENTITY_TABLE = "xmeta_entity";
    public static final String XMETA_ENTITY_FIELD_TABLE = "xmeta_entity_field";

    public static Table generateEntityTable(Database database) {
        Table table = new Table(database, null, null, XMETA_ENTITY_TABLE, null);
        TableColumn column = new TableColumn(table);
        column.setName("id");
        column.setLength(64);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);
        table.getPrimaryColumns().add(column);

        column = new TableColumn(table);
        column.setName("name");
        column.setLength(500);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("displayName");
        column.setLength(500);
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("pluralDisplayName");
        column.setLength(500);
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);


        column = new TableColumn(table);
        column.setName("table_");
        column.setLength(100);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("description");
        column.setLength(1000);
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("lastSyncTime");
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(Long.class));
        table.getColumnsMap().put(column.getName(), column);

        return table;
    }

    public static Table generateEntityField(Database database) {
        Table table = new Table(database, null, null, XMETA_ENTITY_FIELD_TABLE, null);
        TableColumn column = new TableColumn(table);
        column.setName("id");
        column.setLength(64);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);
        table.getPrimaryColumns().add(column);

        column = new TableColumn(table);
        column.setName("name");
        column.setLength(500);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("displayName");
        column.setLength(500);
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);


        column = new TableColumn(table);
        column.setName("column_");
        column.setLength(100);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("description");
        column.setLength(1000);
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("dataType");
        column.setLength(50);
        column.setNullable(false);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("javaType");
        column.setLength(100);
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("required");
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(Boolean.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("unique_");
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(Boolean.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("searchable");
        column.setNullable(true);
        column.setType(JdbcUtil.sqlTypeFor(Boolean.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("properties");
        column.setNullable(true);
        column.setLength(65536);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        column = new TableColumn(table);
        column.setName("entity_id");
        column.setNullable(true);
        column.setLength(64);
        column.setType(JdbcUtil.sqlTypeFor(String.class));
        table.getColumnsMap().put(column.getName(), column);

        return table;
    }
}
