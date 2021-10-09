package io.xmeta.admin.repository;

import io.xmeta.admin.domain.EnvironmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface EnvironmentRepository extends JpaRepository<EnvironmentEntity, String> {

}
