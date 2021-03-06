package io.xmeta.admin.repository;

import io.xmeta.admin.domain.AppRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface AppRoleRepository extends JpaRepository<AppRoleEntity, String>, JpaSpecificationExecutor<AppRoleEntity> {

}
