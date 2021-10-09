package io.xmeta.admin.repository;

import io.xmeta.admin.domain.BlockVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface BlockVersionRepository extends JpaRepository<BlockVersionEntity, String>, JpaSpecificationExecutor<BlockVersionEntity> {

    @Query("from BlockVersionEntity ev where ev.block.id = :blockId order by ev.versionNumber asc")
    List<BlockVersionEntity> findBlockVersions(@Param("blockId") String blockId);

    @Query("from BlockVersionEntity ev where ev.block.id = :blockId and ev.versionNumber = 0")
    BlockVersionEntity findBlockCurrentVersion(@Param("blockId") String blockId);
}
