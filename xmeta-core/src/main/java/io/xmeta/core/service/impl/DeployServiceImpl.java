package io.xmeta.core.service.impl;

import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.DeployService;
import io.xmeta.core.service.MetaLoaderService;
import io.xmeta.core.utils.EntityConverter;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeployServiceImpl implements DeployService {

    private MetaLoaderService metaLoaderService;

    public DeployServiceImpl(MetaLoaderService metaLoaderService) {
        this.metaLoaderService = metaLoaderService;
    }

    /**
     * @param entities   数据模型
     * @param dataSource 数据源
     * @param settings   配置
     * @param saveMeta   数据定义到远程数据源
     * @param forceNull  强制字段使用null
     */
    @Override
    public void deploy(List<Entity> entities, DataSource dataSource, Map<String, Object> settings, boolean saveMeta, boolean forceNull) {
        if (settings == null) {
            settings = new HashMap<>();
        }
        settings.put("hibernate.connection.datasource", dataSource);
        ServiceRegistry serviceRegistry = buildCfg(settings);
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        try {
            metadataSources.addInputStream(EntityConverter.createMappingStream(entities));
        } catch (Exception e) {
            e.printStackTrace();
        }
        MetadataImplementor metadata = (MetadataImplementor) metadataSources.buildMetadata();
        SchemaExport schemaExport = new SchemaExport();
        // schemaExport.setOutputFile("hbm2schema.sql");
        schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
        serviceRegistry.close();

        if (saveMeta) {
            this.metaLoaderService.save(dataSource, entities);
        }

    }

    public static ServiceRegistry buildCfg(Map<String, Object> settings) {
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        ssrb.applySettings(settings);
        return ssrb.build();
    }
}
