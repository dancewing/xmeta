package io.xmeta.web.controller;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import io.xmeta.web.swagger.MetaSwaggerApiService;
import io.xmeta.web.swagger.SwaggerProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = SwaggerProvider.API_URI)
@ApiIgnore
public class MetaOpenAPIController {

    private final MetaSwaggerApiService metaSwaggerApiService;

    public MetaOpenAPIController(MetaSwaggerApiService metaSwaggerApiService) {
        this.metaSwaggerApiService = metaSwaggerApiService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> metaDocs() {
        OpenAPI openAPI = metaSwaggerApiService.load();
        return ResponseEntity.ok(Json.pretty(openAPI));
    }
}
