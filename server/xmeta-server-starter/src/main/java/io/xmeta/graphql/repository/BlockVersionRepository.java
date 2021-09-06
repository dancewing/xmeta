package io.xmeta.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.xmeta.graphql.domain.BlockVersionEntity;

import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Repository
public interface BlockVersionRepository extends JpaRepository<BlockVersionEntity, String> {
}
