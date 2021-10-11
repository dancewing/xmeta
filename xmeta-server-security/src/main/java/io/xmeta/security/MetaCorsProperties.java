package io.xmeta.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "xmetadata.security.cors", ignoreUnknownFields = false)
public class MetaCorsProperties extends CorsConfiguration {
}
