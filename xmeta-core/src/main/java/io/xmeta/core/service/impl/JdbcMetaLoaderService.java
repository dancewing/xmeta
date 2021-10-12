package io.xmeta.core.service.impl;

import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.MetaLoaderService;

import javax.sql.DataSource;
import java.util.List;

public class JdbcMetaLoaderService implements MetaLoaderService {
    @Override
    public List<Entity> load(DataSource dataSource) {
        return null;
    }
}
