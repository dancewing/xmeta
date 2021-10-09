package io.xmeta.admin.domain;

import io.xmeta.admin.util.CustomEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners({CustomEntityListener.class})
public abstract class BaseEntity {

    public abstract String getId();

    public abstract void setId(String id);
}
