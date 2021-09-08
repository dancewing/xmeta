package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, String> {

}
