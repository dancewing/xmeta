package io.xmeta.admin.controller;

import io.xmeta.admin.model.Auth;
import io.xmeta.admin.model.LoginInput;
import io.xmeta.admin.model.User;
import io.xmeta.admin.service.AuthService;
import io.xmeta.core.api.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody LoginInput data) {
        Auth auth = authService.login(data);
        if (auth != null) {
            return R.data(auth.getToken());
        }
        return R.fail("登录失败");
    }

    @GetMapping("/me")
    public R<User> me() {
        return R.data(this.authService.currentUser());
    }
}
