package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.EntityFieldEntity;
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
public interface EntityFieldRepository extends JpaRepository<EntityFieldEntity, String>, JpaSpecificationExecutor<EntityFieldEntity> {

    @Query("delete from EntityFieldEntity ef where ef.entityVersion.id  = :entityVersionId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByEntityVersionId(@Param("entityVersionId") String entityVersionId);

    @Query("delete from EntityFieldEntity ef where ef.entityVersion.id  = :entityVersionId and ef.permanentId = :permanentId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByEntityVersionAndPermanentId(@Param("permanentId") String permanentId,
                                             @Param("entityVersionId") String entityVersionId);

    @Query("from EntityFieldEntity ef where ef.entityVersion.id = :entityVersionId order by ef.createdAt asc ")
    List<EntityFieldEntity> getFields(@Param("entityVersionId") String entityVersionId);
}
