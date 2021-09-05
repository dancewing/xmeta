package io.xmeta.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.UserRoleEntity;

import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {

    @Query("select distinct ur.role from UserRoleEntity ur left join UserEntity u on ur.userId = u.id where u.accountId = :accountId ")
    List<String> findRoles(@Param("accountId") String accountId);

}
