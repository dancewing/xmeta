package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.BlockVersionEntity;
import io.xmeta.graphql.model.BlockVersion;
import io.xmeta.graphql.util.ObjectMapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", uses = {BlockMapper.class, CommitMapper.class})
public interface BlockVersionMapper extends BaseMapper<BlockVersion, BlockVersionEntity> {
    @Mapping(target = "inputParameters", ignore = true)
    @Mapping(target = "outputParameters", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "builds", ignore = true)
    BlockVersionEntity toEntity(BlockVersion dto);

    BlockVersion toDto(BlockVersionEntity entity);

    default byte[] toBytes(Map<String, Object> value) {
        return ObjectMapperUtils.toBytes(value);
    }

    default Map<String, Object> toMap(byte[] bytes) {
        return ObjectMapperUtils.toMap(bytes);
    }
}
