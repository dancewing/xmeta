package io.xmeta.web.controller;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaException;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.web.handler.Context;
import io.xmeta.web.handler.Request;
import io.xmeta.web.service.RestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.xmeta.web.Constants.META_API_URL;

@RestController
@RequestMapping(value = META_API_URL)
public class MetaDataController {

    private final RestService restService;
    private final MetaEntityService metaEntityService;

    public MetaDataController(RestService restService, MetaEntityService metaEntityService) {
        this.restService = restService;
        this.metaEntityService = metaEntityService;
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<List<TestRestController.User>> getAll(@PathVariable String entityId,
                                                                @RequestHeader(required = false) HttpHeaders headers,
                                                                @RequestParam(required = false) Map<String, Object> params,
                                                                HttpServletRequest servletRequest) {

        //TODO 处理前，权限检查
        Request request = Request.of(headers, HttpMethod.resolve(servletRequest.getMethod()), params, null);
        Context context = new Context(request, entityId, ActionType.LIST);

        this.restService.process(context);

        //TODO 输出 header 到 servlet response

        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

    @PostMapping("/{entityId}/search")
    public ResponseEntity<Page<TestRestController.User>> search(@PathVariable String entityId,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false)  Integer size,
                                                                @RequestBody TestRestController.SearchUser searchUser) {
        return ResponseEntity.ok(Page.empty());
    }

    @PostMapping(value = "/{entityId}")
    public ResponseEntity<Object> createUser(@PathVariable String entityId,
                                                              @RequestHeader(required = false) HttpHeaders headers,
                                                              @RequestParam(required = false) Map<String, Object> params,
                                                              @RequestBody(required = false) Map<String, Object> body,
                                                              HttpServletRequest servletRequest) {

        Request request = Request.of(headers, HttpMethod.resolve(servletRequest.getMethod()), params, body);
        Context context = new Context(request, entityId, ActionType.CREATE);
        this.restService.process(context);
        return ResponseEntity.ok(context.getData());
    }

    @GetMapping("/{entityId}/{id}")
    public ResponseEntity<TestRestController.User> getOne(@PathVariable String entityId, @PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{entityId}/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String entityId, @PathVariable String id,
                                             @RequestHeader(required = false) HttpHeaders headers,
                                             @RequestParam(required = false) Map<String, Object> params,
                                             @RequestBody(required = false) Map<String, Object> body,
                                             HttpServletRequest servletRequest) {
        Entity entity = metaEntityService.getEntity(entityId);
        if (entity == null) {
            throw new MetaException("找不到对应的数据模型: " + entityId);
        }
        EntityField pk = EntityFieldUtils.findPK(entity).orElseThrow(MetaException::new);
        body.put(pk.getName(), id);
        Request request = Request.of(headers, HttpMethod.resolve(servletRequest.getMethod()), params, body);
        Context context = new Context(request, entityId, ActionType.UPDATE);
        this.restService.process(context);
        return ResponseEntity.ok(context.getData());
    }

    @DeleteMapping("/{entityId}/{id}")
    public ResponseEntity<TestRestController.User> deleteUser(@PathVariable String entityId, @PathVariable String id) {
        return ResponseEntity.ok(null);
    }
}
