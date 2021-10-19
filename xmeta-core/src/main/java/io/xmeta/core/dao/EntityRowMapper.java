package io.xmeta.core.dao;

import io.xmeta.core.domain.Entity;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.EntityUtils;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EntityRowMapper implements RowMapper<Map<String, Object>> {

    private final Entity entity;
    private final JdbcConverter converter;

    public EntityRowMapper(Entity entity, JdbcConverter converter) {
        this.entity = entity;
        this.converter = converter;
    }

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

        Map<String, Object> row = new HashMap<>();

        EntityUtils.doWithAll(entity, field -> {
            if (!EntityFieldUtils.isSelectable(field)) {
                return;
            }
            try {
                row.put(field.getName(), rs.getObject(field.getName()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return row;
    }
}
