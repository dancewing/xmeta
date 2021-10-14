package io.xmeta.web.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api
@RequestMapping("/test")
public class TestRestController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ok");
    }
}
