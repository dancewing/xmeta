package io.xmeta.graphql.service;

import io.xmeta.graphql.model.*;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import io.xmeta.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AccountService accountService;
    private final WorkspaceService workspaceService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @Transactional
    public Auth signup(SignupInput data) {


        String hashedPassword = passwordEncoder.encode(data.getPassword());

        Account account = new Account();
        account.setEmail(data.getEmail());
        account.setFirstName(data.getFirstName());
        account.setLastName(data.getLastName());
        account.setPassword(hashedPassword);
        account = this.accountService.createAccount(account);

        Workspace workspace = this.workspaceService.createWorkspace(account.getId(), data.getWorkspaceName());

        User user = null;
        if (workspace.getUsers() != null && workspace.getUsers().size() > 0) {
            user = workspace.getUsers().get(0);
        }
        if (user == null) {
            throw new RuntimeException("can't get default user from workspace");
        }

        this.accountService.setCurrentUser(account.getId(), user.getId());

        return new Auth(createToken(data.getEmail(), data.getPassword(), true));
    }

    private String createToken(String email, String password, boolean rememberMe) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication, rememberMe);
    }

    public Auth login(LoginInput data) {
        try {
            return new Auth(createToken(data.getEmail(), data.getPassword(), true));
        } catch (Exception ex) {

        }
        return null;
    }

    public User currentUser() {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        if (authUser != null) {
            return this.userService.getUser(authUser.getUserId());
        }
        return null;
    }
}
