package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.EntityVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface EntityVersionRepository extends JpaRepository<EntityVersionEntity, String>, JpaSpecificationExecutor<EntityVersionEntity> {

    @Query("from EntityVersionEntity ev where ev.entity.id = :entityId order by ev.versionNumber asc")
    List<EntityVersionEntity> findEntityVersions(@Param("entityId") String entityId);

    @Query("from EntityVersionEntity ev where ev.entity.id = :entityId")
    Page<EntityVersionEntity> findEntityVersions(@Param("entityId") String entityId, Pageable page);

    @Query("from EntityVersionEntity ev where ev.entity.id = :entityId and ev.versionNumber = :versionNumber")
    EntityVersionEntity findEntityVersion(@Param("entityId") String entityId, @Param("versionNumber") Integer versionNumber);

    @Query("from EntityVersionEntity ev where ev.entity.id = :entityId and ev.versionNumber <> 0 " +
            "order by ev.versionNumber desc")
    List<EntityVersionEntity> getLatestVersions(@Param("entityId") String entityId, Pageable pageable);

    @Query("from EntityVersionEntity ev where ev.entity.id = :entityId and ev.versionNumber = 0 ")
    Optional<EntityVersionEntity> getCurrentVersion(String entityId);
}
