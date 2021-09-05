package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AccountEntity;
import io.xmeta.graphql.domain.UserEntity;
import io.xmeta.graphql.domain.UserRoleEntity;
import io.xmeta.graphql.domain.WorkspaceEntity;
import io.xmeta.graphql.model.Auth;
import io.xmeta.graphql.model.LoginInput;
import io.xmeta.graphql.model.SignupInput;
import io.xmeta.graphql.repository.AccountRepository;
import io.xmeta.graphql.repository.UserRepository;
import io.xmeta.graphql.repository.UserRoleRepository;
import io.xmeta.graphql.repository.WorkspaceRepository;
import io.xmeta.security.jwt.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class AccountService extends BaseService<AccountRepository, AccountEntity, String> {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, WorkspaceRepository workspaceRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public Auth signup(SignupInput data) {
        String hashedPassword = passwordEncoder.encode(data.getPassword());
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCreatedAt(ZonedDateTime.now());
        accountEntity.setUpdatedAt(ZonedDateTime.now());
        accountEntity.setEmail(data.getEmail());
        accountEntity.setFirstName(data.getFirstName());
        accountEntity.setLastName(data.getLastName());
        accountEntity.setPassword(hashedPassword);
       // accountEntity.setCurrentUserId("");
       // accountEntity.setGithubId("");

        this.accountRepository.save(accountEntity);

        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.setCreatedAt(ZonedDateTime.now());
        workspaceEntity.setUpdatedAt(ZonedDateTime.now());
        workspaceEntity.setName(data.getWorkspaceName());

        this.workspaceRepository.save(workspaceEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setUpdatedAt(ZonedDateTime.now());
        userEntity.setAccountId(accountEntity.getId());
        userEntity.setWorkspaceId(workspaceEntity.getId());
        userEntity.setIsOwner(true);
        this.userRepository.save(userEntity);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setCreatedAt(ZonedDateTime.now());
        userRoleEntity.setUpdatedAt(ZonedDateTime.now());
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRole("ORGANIZATION_ADMIN");

        this.userRoleRepository.save(userRoleEntity);

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
}
