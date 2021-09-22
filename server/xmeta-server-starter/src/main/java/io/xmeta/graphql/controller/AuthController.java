package io.xmeta.graphql.controller;

import io.xmeta.graphql.model.Auth;
import io.xmeta.graphql.model.LoginInput;
import io.xmeta.graphql.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginInput data) {
        Auth auth = authService.login(data);
        if (auth!=null) {
            return ResponseEntity.ok(auth.getToken());
        }
        return ResponseEntity.internalServerError().body("error");
    }
}
