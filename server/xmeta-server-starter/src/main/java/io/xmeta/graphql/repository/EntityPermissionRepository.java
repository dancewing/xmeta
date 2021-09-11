package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.EntityPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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
public interface EntityPermissionRepository extends JpaRepository<EntityPermissionEntity, String>, JpaSpecificationExecutor<EntityPermissionEntity> {

    @Query("delete from EntityPermissionEntity epe where epe.entityVersion.id  = :entityVersionId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByEntityVersionId(@Param("entityVersionId") String entityVersionId);

    @Query("from EntityPermissionEntity upe where upe.action = :action and upe.entityVersion.id = :entityVersionId")
    List<EntityPermissionEntity> getEntitiesByActionAndVersion(@Param("action") String action, @Param(
            "entityVersionId") String entityVersionId);

    @Query("from EntityPermissionEntity upe where upe.entityVersion.id = :entityVersionId")
    List<EntityPermissionEntity> getPermissions(@Param("entityVersionId") String entityVersionId);
}
