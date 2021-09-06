package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.EntityFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface EntityFieldRepository extends JpaRepository<EntityFieldEntity, String>, JpaSpecificationExecutor<EntityFieldEntity> {

    @Query("delete from EntityFieldEntity ef where ef.entityVersion.id  = :entityVersionId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByEntityVersionId(@Param("entityVersionId") String entityVersionId);

}
