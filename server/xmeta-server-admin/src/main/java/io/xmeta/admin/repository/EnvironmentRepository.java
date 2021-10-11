package io.xmeta.admin.repository;

import io.xmeta.admin.domain.EnvironmentEntity;
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
public interface EnvironmentRepository extends JpaRepository<EnvironmentEntity, String> {
    @Query("from EnvironmentEntity ev where ev.app.id = :appId order by ev.updatedAt desc")
    List<EnvironmentEntity> getAllByApp(@Param("appId") String appId);
}
