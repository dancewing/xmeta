package io.xmeta.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static io.xmeta.web.Constants.META_API_URL;

@RestController
@RequestMapping(value = META_API_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MetaDataController {


    @GetMapping("/{entityId}")
    public ResponseEntity<List<TestRestController.User>> getAll(@PathVariable String entityId) {
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
