package io.xmeta.graphql.repository;

import io.xmeta.graphql.domain.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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
public interface BlockRepository extends JpaRepository<BlockEntity, String>, JpaSpecificationExecutor<BlockEntity> {


    @Query("from BlockEntity b where b.lockedByUser.id = :userId and b.app.id = :appId")
    List<BlockEntity> findChangedBlocks(@Param("appId") String appId, @Param("userId") String userId);

    @Query("update BlockEntity set lockedByUser = null, lockedAt = null where id = :blockId")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void releaseLock(@Param("blockId") String blockId);

}
