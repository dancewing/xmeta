package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.EntityVersionEntity;

import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface EntityVersionRepository extends JpaRepository<EntityVersionEntity, String>, JpaSpecificationExecutor<EntityVersionEntity> {

    @Query("from EntityVersionEntity ev where ev.entity.id = :entityId order by ev.versionNumber asc")
    List<EntityVersionEntity> findEntityVersions(@Param("entityId") String entityId);

}
