package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AccountEntity;
import io.xmeta.graphql.mapper.AccountMapper;
import io.xmeta.graphql.model.Account;
import io.xmeta.graphql.repository.AccountRepository;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

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
        //检查email 是否重复

        Optional<AccountEntity> optionalAccount = this.accountRepository.findOneByEmailIgnoreCase(account.getEmail());
        if (optionalAccount.isPresent()) {
            throw new RuntimeException(account.getEmail() + " is already registered");
        }

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

    public Account currentAccount() {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        if (authUser != null) {
            return this.accountMapper.toDto(this.accountRepository.getById(authUser.getAccountId()));
        }
        return null;
    }
}
