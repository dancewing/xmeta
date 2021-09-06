package io.xmeta.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.EntityPermissionRoleEntity;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface EntityPermissionRoleRepository extends JpaRepository<EntityPermissionRoleEntity, String> {

    @Query("delete from EntityPermissionRoleEntity epr where epr.entityVersionId  = :entityVersionId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByEntityVersionId(@Param("entityVersionId") String entityVersionId);
}
