package io.xmeta.web.swagger;

import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.MetaLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;

import javax.sql.DataSource;
import java.util.List;

/**
 *
 */
@Component
@Slf4j
public class MetaApiListingBuilderPluginImpl implements ApiListingBuilderPlugin {

    private MetaLoaderService metaLoaderService;

    private DataSource dataSource;

    public MetaApiListingBuilderPluginImpl(MetaLoaderService metaLoaderService, DataSource dataSource) {
        this.metaLoaderService = metaLoaderService;
        this.dataSource = dataSource;
    }

    @Override
    public void apply(ApiListingContext apiListingContext) {
        List<Entity> entities = this.metaLoaderService.load(dataSource);
        log.info("get meta api list");
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
