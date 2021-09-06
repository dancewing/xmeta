package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.AccountEntity;
import io.xmeta.graphql.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface AccountMapper extends BaseMapper<Account, AccountEntity> {

    Account toDto(AccountEntity entity);

    @Mapping(target = "currentUser", ignore = true)
    AccountEntity toEntity(Account dto);
}
