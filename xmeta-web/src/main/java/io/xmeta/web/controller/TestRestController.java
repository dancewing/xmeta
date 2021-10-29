package io.xmeta.web.controller;

import io.xmeta.core.filter.DateTimeFilter;
import io.xmeta.core.filter.IntFilter;
import io.xmeta.core.filter.StringFilter;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestRestController {

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<User>> search(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer size,
                                             @RequestBody SearchUser searchUser) {
        return ResponseEntity.ok(Page.empty());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(null);
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private Integer age;
        private LocalDateTime birthday;
        private Long lon;
        private Double dou;
        private List<Role> roles;
    }



    @Data
    public static class SearchUser {
        private StringFilter id;
        private String name;
        private IntFilter age;
        private DateTimeFilter birthday;
    }

    @Data
    public static class Role {
        private String id;
        private String name;
        private List<User> users;
    }
}
