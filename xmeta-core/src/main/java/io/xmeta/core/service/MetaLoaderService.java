package io.xmeta.core.service;

import io.xmeta.core.domain.Entity;

import javax.sql.DataSource;
import java.util.List;

public interface MetaLoaderService {
    List<Entity> load(DataSource dataSource);
}
