package io.xmeta.core.dao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@ConfigurationProperties(prefix = "meta.jdbc")
@Data
public class MetaJdbcProperties {
    private boolean showSQL = true;
    private boolean showParameters = true;
}
