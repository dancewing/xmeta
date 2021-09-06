package io.xmeta.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.EntityPermissionFieldEntity;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface EntityPermissionFieldRepository extends JpaRepository<EntityPermissionFieldEntity, String> {

    @Query("delete from EntityPermissionFieldEntity epf where epf.entityVersionId  = :entityVersionId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByEntityVersionId(@Param("entityVersionId") String entityVersionId);
}
