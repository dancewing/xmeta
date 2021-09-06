package io.xmeta.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.AccountEntity;

import java.util.Optional;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findOneByEmailIgnoreCase(String email);

    @Query("update AccountEntity set currentUser.id = :userId WHERE id =:accountId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void setCurrentUser(@Param("accountId") String accountId, @Param("userId") String userId);

}
