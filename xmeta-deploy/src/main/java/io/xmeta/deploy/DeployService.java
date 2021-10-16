package io.xmeta.deploy;

import io.xmeta.core.domain.Entity;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 将数据模型定义部署到数据库
 *
 * @author jeff_qian
 */
public interface DeployService {

    /**
     * 部署
     *
     * @param entities   数据模型
     * @param dataSource 数据源
     * @param settings   配置
     * @param saveMeta   数据定义到远程数据源
     * @param forceNull  强制字段使用null
     */
    void deploy(List<Entity> entities, DataSource dataSource, Map<String, Object> settings, boolean saveMeta, boolean forceNull);
}
