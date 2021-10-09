package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.BlockEntity;
import io.xmeta.graphql.model.Block;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AppMapper.class})
public interface BlockMapper extends BaseMapper<Block, BlockEntity> {
    @Mapping(target = "lockedByUser", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "blocks", ignore = true)
    @Mapping(target = "versions", ignore = true)
    BlockEntity toEntity(Block dto);

    @Mapping(target = "lockedByUser", ignore = true)
    @Mapping(target = "versionNumber", ignore = true)
    @Mapping(target = "lockedByUserId", source = "lockedByUser.id")
    Block toDto(BlockEntity entity);
}
