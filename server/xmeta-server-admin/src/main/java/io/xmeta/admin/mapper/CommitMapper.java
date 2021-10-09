package io.xmeta.admin.mapper;

import io.xmeta.admin.domain.CommitEntity;
import io.xmeta.graphql.model.Commit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommitMapper extends BaseMapper<Commit, CommitEntity> {
    @Mapping(target = "changes", ignore = true)
    @Mapping(target = "userId", source = "user.id")
    Commit toDto(CommitEntity entity);

    @Mapping(target = "app", ignore = true)
    CommitEntity toEntity(Commit dto);
}
