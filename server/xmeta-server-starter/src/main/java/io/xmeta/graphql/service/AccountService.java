package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AccountEntity;
import io.xmeta.graphql.mapper.AccountMapper;
import io.xmeta.graphql.model.Account;
import io.xmeta.graphql.repository.AccountRepository;
import io.xmeta.graphql.repository.UserRepository;
import io.xmeta.graphql.repository.UserRoleRepository;
import io.xmeta.graphql.repository.WorkspaceRepository;
import io.xmeta.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@Slf4j
public class AccountService extends BaseService<AccountRepository, AccountEntity, String> {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public Account createAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCreatedAt(ZonedDateTime.now());
        accountEntity.setUpdatedAt(ZonedDateTime.now());
        accountEntity.setEmail(account.getEmail());
        accountEntity.setFirstName(account.getFirstName());
        accountEntity.setLastName(account.getLastName());
        accountEntity.setPassword(account.getPassword());
        this.accountRepository.saveAndFlush(accountEntity);
        return this.accountMapper.toDto(accountEntity);
    }

    @Transactional
    public void setCurrentUser(String accountId, String userId) {
        this.accountRepository.setCurrentUser(accountId, userId);
    }
}
