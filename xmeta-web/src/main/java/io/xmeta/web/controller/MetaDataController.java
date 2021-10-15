package io.xmeta.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.xmeta.core.ActionType;
import io.xmeta.web.handler.Context;
import io.xmeta.web.handler.Request;
import io.xmeta.web.handler.HttpRequestImpl;
import io.xmeta.web.service.RestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.xmeta.web.Constants.META_API_URL;

@RestController
@Hidden
@RequestMapping(value = META_API_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MetaDataController {

    private final RestService restService;

    public MetaDataController(RestService restService) {
        this.restService = restService;
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

    @PostMapping("/{entityId}")
    public ResponseEntity<TestRestController.User> createUser(@PathVariable String entityId,
                                                              @RequestBody TestRestController.User user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{entityId}/{id}")
    public ResponseEntity<TestRestController.User> getOne(@PathVariable String entityId, @PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{entityId}/{id}")
    public ResponseEntity<TestRestController.User> updateUser(@PathVariable String entityId, @PathVariable String id, @RequestBody TestRestController.User user) {
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{entityId}/{id}")
    public ResponseEntity<TestRestController.User> deleteUser(@PathVariable String entityId, @PathVariable String id) {
        return ResponseEntity.ok(null);
    }
}
