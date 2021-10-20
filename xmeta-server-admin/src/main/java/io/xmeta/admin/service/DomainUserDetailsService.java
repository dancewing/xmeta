package io.xmeta.admin.service;

import io.xmeta.admin.domain.AccountEntity;
import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.repository.AccountRepository;
import io.xmeta.admin.repository.UserRepository;
import io.xmeta.admin.repository.UserRoleRepository;
import io.xmeta.admin.util.EmailValidator;
import io.xmeta.security.AuthUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@Slf4j
public class DomainUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public DomainUserDetailsService(AccountRepository accountRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        if (EmailValidator.isValid(login)) {
            return accountRepository.findOneByEmailIgnoreCase(login)
                    .map(account -> createSpringSecurityUser(account))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        return null;

//        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//        return userRepository.findOneByUsername(lowercaseLogin)
//                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
//                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    public AuthUserDetail createSpringSecurityUser(AccountEntity account) {
//        if (account.getIsActive() != null && !account.getIsActive()) {
//            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//        }
        UserEntity userEntity = this.userRepository.getById(account.getCurrentUser().getId());
        List<String> roles = this.userRoleRepository.findRoles(account.getId());
        List<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
        AuthUserDetail user = new AuthUserDetail(account.getId(), account.getEmail(),
                account.getPassword(),
                grantedAuthorities);
        user.setUserId(userEntity.getId());
        user.setWorkspaceId(userEntity.getWorkspace().getId());
        return user;
    }
}
