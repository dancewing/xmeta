package io.xmeta.graphql.mapper;

import io.xmeta.graphql.domain.CommitEntity;
import io.xmeta.graphql.model.Commit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommitMapper extends BaseMapper<Commit, CommitEntity> {
    @Mapping(target = "changes", ignore = true)
    Commit toDto(CommitEntity entity);

    CommitEntity toEntity(Commit dto);
}
