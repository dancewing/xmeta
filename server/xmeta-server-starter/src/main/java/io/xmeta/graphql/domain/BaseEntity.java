package io.xmeta.graphql.domain;

import io.xmeta.graphql.util.CustomEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners({CustomEntityListener.class})
public abstract class BaseEntity {

    public abstract String getId();

    public abstract void setId(String id);
}
