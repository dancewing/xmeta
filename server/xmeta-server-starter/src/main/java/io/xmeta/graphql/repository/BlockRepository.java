package io.xmeta.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.BlockEntity;

import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface BlockRepository extends JpaRepository<BlockEntity, String> {


    @Query("from BlockEntity b where b.lockedByUser.id = :userId and b.app.id = :appId")
    List<BlockEntity> findChangedBlocks(@Param("appId") String appId, @Param("userId") String userId);

}
