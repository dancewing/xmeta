package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.UserEntity;
import io.xmeta.graphql.domain.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByAccountId(String accountId);

//    @Query("select distinct ue from UserEntity ue where ue.accountId = :accountId ")
//    Optional<UserEntity> findByAccountId(@Param("accountId") String accountId);

    @Query("select distinct ue.workspace from UserEntity ue where ue.account.id = :accountId ")
    List<WorkspaceEntity> findWorkspaceByAccount(@Param("accountId") String accountId);
}
