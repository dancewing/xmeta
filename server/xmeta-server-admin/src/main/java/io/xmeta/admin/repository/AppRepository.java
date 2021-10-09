package io.xmeta.admin.repository;

import io.xmeta.admin.domain.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Repository
public interface AppRepository extends JpaRepository<AppEntity, String>, JpaSpecificationExecutor<AppEntity> {

    @Query("from AppEntity ae inner join ae.workspace ws inner join ws.users u where ae.id = :appId and u.id = " +
            ":userId ")
    AppEntity findAppEntity(@Param("appId") String appId, @Param("userId") String userId);
}
