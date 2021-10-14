package io.xmeta.admin.service;

import io.xmeta.admin.domain.AccountEntity;
import io.xmeta.admin.model.*;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import io.xmeta.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final DomainUserDetailsService domainUserDetailsService;

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

    @Transactional
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
            try {
                return this.userService.getUser(authUser.getUserId());
            } catch (Exception ex) {
                throw new AuthenticationServiceException("un login");
            }
        }
        return null;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Auth setCurrentWorkspace(WhereUniqueInput data) {

        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        AccountEntity accountEntity = this.accountService.getById(authUser.getAccountId());

        List<User> users = this.userService.findUsers(data.getId(), authUser.getAccountId());
        if (users.size() == 0) {
            throw new RuntimeException("This account does not have an active user records in the selected workspace " +
                    "or workspace not found " + data.getId());
        }
        //用户已经登录，重新制作token
        User user = users.get(0);
        this.accountService.setCurrentUser(authUser.getAccountId(), user.getId());
        AuthUserDetail authUserDetail = this.domainUserDetailsService.createSpringSecurityUser(accountEntity);
        Authentication newAuth =
                new UsernamePasswordAuthenticationToken(authUserDetail, null, authUserDetail.getAuthorities());
        return new Auth(tokenProvider.createToken(newAuth, true));
    }
}
