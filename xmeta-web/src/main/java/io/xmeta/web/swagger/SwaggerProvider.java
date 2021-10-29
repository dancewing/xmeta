package io.xmeta.web.swagger;

import org.springframework.core.env.Environment;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * 聚合接口文档， 在原来的swagger resource 基础上添加meta API的resource
 */
public class SwaggerProvider extends InMemorySwaggerResourcesProvider {
	public static final String API_URI = "/v2/api-docs-ext";

	public SwaggerProvider(Environment environment, DocumentationCache documentationCache) {
		super(environment, documentationCache);
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = super.get();
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName("Meta Swagger");
		swaggerResource.setLocation(API_URI);
		swaggerResource.setSwaggerVersion("1.0");
		resources.add(swaggerResource);
		return resources;
	}
}
