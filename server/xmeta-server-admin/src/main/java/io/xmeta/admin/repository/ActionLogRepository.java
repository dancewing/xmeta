package io.xmeta.admin.repository;

import io.xmeta.admin.domain.ActionLogEntity;
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
public interface ActionLogRepository extends JpaRepository<ActionLogEntity, String> {

    @Query("from ActionLogEntity al where al.step.id = :stepId ")
    List<ActionLogEntity> findActionLogs(@Param("stepId") String stepId);
}
