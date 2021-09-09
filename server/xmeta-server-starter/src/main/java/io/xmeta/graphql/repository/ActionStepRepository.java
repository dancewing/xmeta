package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.ActionStepEntity;
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
public interface ActionStepRepository extends JpaRepository<ActionStepEntity, String> {

    @Query("from ActionStepEntity ase where ase.action.id = :actionId ")
    List<ActionStepEntity> findActionSteps(@Param("actionId") String actionId);
}
