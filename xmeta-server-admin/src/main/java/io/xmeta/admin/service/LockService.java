package io.xmeta.admin.service;

import io.xmeta.admin.domain.BlockEntity;
import io.xmeta.admin.domain.EntityEntity;
import io.xmeta.admin.domain.UserEntity;
import io.xmeta.admin.repository.BlockRepository;
import io.xmeta.admin.repository.EntityRepository;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
@Slf4j
public class LockService {

    private final EntityRepository entityRepository;
    private final BlockRepository blockRepository;

    @Transactional
    public EntityEntity acquireEntityLock(String entityId) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        EntityEntity entity = this.entityRepository.getById(entityId);
        if (log.isDebugEnabled()) {
            log.debug("Get entity by id: {}", entityId);
        }
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

    @Transactional
    public BlockEntity acquireBlockLock(String blockId) {
        AuthUserDetail authUser = SecurityUtils.getAuthUser();
        BlockEntity entity = this.blockRepository.getById(blockId);
        if (entity.getLockedByUser() != null && StringUtils.equals(entity.getLockedByUser().getId(),
                authUser.getUserId())) {
            return entity;
        }
        if (entity.getLockedByUser() != null) {
            throw new RuntimeException("Block " + blockId + " is already locked by another user - " + entity.getLockedByUser().getId());
        }
        entity.setLockedAt(ZonedDateTime.now());
        UserEntity user = new UserEntity();
        user.setId(authUser.getUserId());
        entity.setLockedByUser(user);
        this.blockRepository.saveAndFlush(entity);
        return entity;
    }

    @Transactional
    public void releaseEntityLock(String entityId) {
        this.entityRepository.releaseLock(entityId);
    }

    @Transactional
    public void releaseBlockLock(String blockId) {
        this.blockRepository.releaseLock(blockId);
    }
}
