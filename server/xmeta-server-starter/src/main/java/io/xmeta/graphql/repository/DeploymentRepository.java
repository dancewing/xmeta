package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.DeploymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface DeploymentRepository extends JpaRepository<DeploymentEntity, String> {

}
