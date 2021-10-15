package io.xmeta.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.OpenAPI;
import io.xmeta.web.swagger.MetaSwaggerApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Hidden
@RequestMapping("/meta-docs")
public class MetaOpenAPIController {

    private final MetaSwaggerApiService metaSwaggerApiService;

    public MetaOpenAPIController(MetaSwaggerApiService metaSwaggerApiService) {
        this.metaSwaggerApiService = metaSwaggerApiService;
    }

    @GetMapping
    public ResponseEntity<OpenAPI> metaDocs() {
        return ResponseEntity.ok(metaSwaggerApiService.load());
    }

    @GetMapping("/swagger-config")
    public ResponseEntity<Map<String, String>> swaggerConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("configUrl", "/meta-docs/swagger-config");
        config.put("url", "/meta-docs");
        return ResponseEntity.ok(config);
    }
}
