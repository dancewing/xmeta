package io.xmeta.admin.controller;

import io.xmeta.admin.model.Auth;
import io.xmeta.admin.model.LoginInput;
import io.xmeta.admin.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (auth != null) {
            return ResponseEntity.ok(auth.getToken());
        }
        return ResponseEntity.internalServerError().body("error");
    }
}
