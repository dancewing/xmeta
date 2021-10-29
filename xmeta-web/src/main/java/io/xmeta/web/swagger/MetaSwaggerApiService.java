package io.xmeta.web.swagger;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.filter.BooleanFilter;
import io.xmeta.core.filter.DateTimeFilter;
import io.xmeta.core.filter.IntFilter;
import io.xmeta.core.filter.StringFilter;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.web.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MetaSwaggerApiService {

    private MetaEntityService metaEntityService;

    @Value(Constants.META_API_URL)
    private String apiDocPath;

    public MetaSwaggerApiService(MetaEntityService metaEntityService) {
        this.metaEntityService = metaEntityService;
    }

    public OpenAPI load() {
        List<Entity> entities = this.metaEntityService.load();

        OpenAPI openApi = new OpenAPI()
                .info(new Info().title("SpringShop API")
                        .description("Spring shop sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
        Paths paths = new Paths();

        Components components = new Components();
        components.setSchemas(new HashMap<>());
        for (Entity entity : entities) {
            paths.addPathItem(this.apiDocPath + "/" + entity.getId(), createRestPathItem(entity));
            paths.addPathItem(this.apiDocPath + "/" + entity.getId() + "/{id}", createRestParamPathItem(entity));
            openApi.addTagsItem(new Tag().name(entity.getName()).description(StringUtils.defaultString(entity.getDescription(), "")));

            addEntitySchema(components, entity);
        }

        addCommonSearchSchema(components);

        openApi.components(components);
        openApi.paths(paths);
        return openApi;
    }

    private PathItem createRestPathItem(Entity entity) {
        PathItem pathItem = new PathItem();
        pathItem.get(createLoadAllOperation(entity));
        pathItem.post(createCreatePostOperation(entity));
        return pathItem;
    }

    private PathItem createRestParamPathItem(Entity entity) {
        PathItem pathItem = new PathItem();
        pathItem.get(createGetOneOperation(entity));
        pathItem.post(createUpdateOperation(entity));
        pathItem.delete(createDeleteOperation(entity));
        return pathItem;
    }

    private Operation createCreatePostOperation(Entity entity) {
        Operation operation = new Operation();
        operation.setDescription(entity.getDescription());
        operation.setSummary("create new record for " + entity.getName());
        operation.setOperationId("save" + entity.getName());
        operation.addTagsItem(entity.getName());

        operation.setRequestBody(createRequestBody(entity));

        operation.setResponses(createEntityResponse(entity));
        List<Parameter> parameters = new ArrayList<>();
        operation.setParameters(parameters);
        return operation;
    }


    private RequestBody createRequestBody(Entity entity) {
        RequestBody requestBody = new RequestBody();
        Content content = new Content();
        content.put(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                new MediaType()
                        .schema(new ObjectSchema().$ref("#/components/schemas/" + entity.getName())));
        requestBody.content(content);
        requestBody.required(true);
        return requestBody;
    }

    private ApiResponses createEntityResponse(Entity entity) {
        ApiResponses apiResponses = new ApiResponses();

        Content content = new Content();
        content.put(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                new MediaType()
                        .schema(new ObjectSchema().$ref("#/components/schemas/" + entity.getName())));

        apiResponses.addApiResponse("200", new ApiResponse().content(content));

        return apiResponses;
    }

    private Operation createLoadAllOperation(Entity entity) {
        Operation operation = new Operation();
        operation.setDescription(entity.getDescription());
        operation.setSummary(entity.getName() + " apis ");
        operation.setOperationId("getAll" + entity.getName());
        operation.addTagsItem(entity.getName());

        operation.setResponses(createEntityResponse(entity));

        List<Parameter> parameters = new ArrayList<>();
        operation.setParameters(parameters);
        return operation;
    }

    private Operation createGetOneOperation(Entity entity) {
        Operation operation = new Operation();
        operation.setDescription(entity.getDescription());
        operation.setSummary(" get one record of " + entity.getName() + " by ID");
        operation.setOperationId("getOne" + entity.getName());
        operation.addTagsItem(entity.getName());

        List<Parameter> parameters = new ArrayList<>();
        Parameter idParam = new Parameter();
        idParam.setName("id");
        idParam.setIn("path");
        idParam.setDescription("ID of " + entity.getName() + " that needs to be loaded");
        parameters.add(idParam);

        operation.setResponses(createEntityResponse(entity));

        RequestBody requestBody = new RequestBody();
        operation.requestBody(requestBody);

        operation.setParameters(parameters);
        return operation;
    }

    private Operation createUpdateOperation(Entity entity) {
        Operation operation = new Operation();
        operation.setDescription(entity.getDescription());
        operation.setSummary("update one record of " + entity.getName() + " by ID");
        operation.setOperationId("update" + entity.getName());
        operation.addTagsItem(entity.getName());

        operation.setRequestBody(createRequestBody(entity));
        operation.setResponses(createEntityResponse(entity));

        List<Parameter> parameters = new ArrayList<>();

        Parameter idParam = new Parameter();
        idParam.setName("id");
        idParam.setIn("path");
        idParam.setDescription("ID of " + entity.getName() + " that needs to be updated");
        parameters.add(idParam);

        operation.setParameters(parameters);
        return operation;
    }

    private Operation createDeleteOperation(Entity entity) {
        Operation operation = new Operation();
        operation.setDescription(entity.getDescription());
        operation.setSummary("delete one record of " + entity.getName() + " by ID");
        operation.setOperationId("update" + entity.getName());
        operation.addTagsItem(entity.getName());

        operation.setResponses(createEntityResponse(entity));

        List<Parameter> parameters = new ArrayList<>();

        Parameter idParam = new Parameter();
        idParam.setName("id");
        idParam.setIn("path");
        idParam.setDescription("ID of " + entity.getName() + " that needs to be delete");
        parameters.add(idParam);

        operation.setParameters(parameters);
        return operation;
    }

    private void addCommonSearchSchema(Components components) {
        addClassSchema(components, StringFilter.class, null);
        addClassSchema(components, BooleanFilter.class, null);
        addClassSchema(components, IntFilter.class, null);
        addClassSchema(components, DateTimeFilter.class, null);
        addClassSchema(components, Pageable.class, "Page");
    }

    private void addEntitySchema(Components components, Entity entity) {
        ObjectSchema objectSchema = new ObjectSchema();
        entity.getFields().forEach(entityField -> {
            String javaType = entityField.getJavaType();
            if (StringUtils.isNotEmpty(javaType)) {
                try {
                    Class<?> cls = Class.forName(javaType);
                    Schema property = PrimitiveType.createProperty(cls);
                    if (property != null) {
                        if (entityField.getRequired()) {
                            //fieldSchema.required(true);
                            objectSchema.addRequiredItem(entityField.getName());
                        }
                        objectSchema.addProperties(entityField.getName(), property);

                    } else {
                        log.error("can't get field schema for :{},{} ", entity.getName(), entityField.getName());
                    }

                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        });
        components.addSchemas(entity.getName(), objectSchema);
    }

    private void addClassSchema(Components components, Class<?> cls, String name) {
        if (StringUtils.isEmpty(name)) {
            name = cls.getSimpleName();
        }
        components.getSchemas().putAll(ModelConverters.getInstance().readAll(cls));
    }

}
