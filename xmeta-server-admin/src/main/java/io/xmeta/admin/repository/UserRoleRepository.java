package io.xmeta.admin.repository;

import io.xmeta.admin.domain.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {

    @Query("select distinct ur.role from UserRoleEntity ur left join UserEntity u on ur.user.id = u.id where u.account.id = :accountId ")
    List<String> findRoles(@Param("accountId") String accountId);

}
