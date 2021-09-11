package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.EntityEntity;
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
public interface EntityRepository extends JpaRepository<EntityEntity, String>, JpaSpecificationExecutor<EntityEntity> {

    @Query("from EntityEntity et where et.name =:name and et.app.id = :appId")
    List<EntityEntity> findEntityByNames(@Param("name") String name, @Param("appId") String appId);

    @Query("select distinct b from EntityEntity b where b.lockedByUser.id = :userId and b.app.id " +
            "= :appId")
    List<EntityEntity> findChangedEntities(@Param("appId") String appId, @Param("userId") String userId);

    @Query("update EntityEntity set lockedByUser = null, lockedAt = null where id = :entityId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void releaseLock(@Param("entityId") String entityId);

    @Query("from EntityEntity et where et.app.id = :appId and et.deletedAt is null")
    List<EntityEntity> findEntitiesByApp(@Param("appId") String appId);

    @Query("select b from EntityEntity b inner join EntityVersionEntity v on b.id = v.entityId where v.commit.id = :commitId")
    List<EntityEntity> findChangedEntities(@Param("commitId") String commitId);

}
