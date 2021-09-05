package io.xmeta.graphql.util;

import org.apache.commons.lang3.StringUtils;
import io.xmeta.graphql.domain.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class CustomEntityListener {
    @PrePersist
    private void beforePersist(Object object) {
        if (object instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) object;

            if (StringUtils.isEmpty(baseEntity.getId())) {
                baseEntity.setId(IDGenerator.nextId());
            }
        }
    }

    @PreUpdate
    private void beforeUpdate(Object object) {
        if (object instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) object;
            // baseEntity.setUpdatedAt(LocalDateTime.now());
            // baseEntity.setUpdatedBy("admin");
        }
    }

    @PreRemove
    private void beforeRemove(Object object) {
        if (object instanceof BaseEntity) {
        }
    }
}
