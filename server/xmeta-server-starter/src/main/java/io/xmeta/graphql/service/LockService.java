package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.EntityEntity;
import io.xmeta.graphql.domain.UserEntity;
import io.xmeta.graphql.repository.EntityRepository;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
@AllArgsConstructor
public class LockService {

    private final EntityRepository entityRepository;

    @Transactional
    public EntityEntity acquireEntityLock(String entityId) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        EntityEntity entity = this.entityRepository.getById(entityId);
        if (entity.getLockedByUser() != null && StringUtils.equals(entity.getLockedByUser().getId(),
                authUser.getUserId())) {
            return entity;
        }
        if (entity.getLockedByUser() != null) {
            throw new RuntimeException("Entity " + entityId + " is already locked by another user - " + entity.getLockedByUser().getId());
        }
        entity.setLockedAt(ZonedDateTime.now());
        UserEntity user = new UserEntity();
        user.setId(authUser.getUserId());
        entity.setLockedByUser(user);
        this.entityRepository.saveAndFlush(entity);
        return entity;
    }
}
