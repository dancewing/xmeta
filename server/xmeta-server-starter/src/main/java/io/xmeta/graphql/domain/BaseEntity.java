package io.xmeta.graphql.domain;

import io.xmeta.graphql.util.CustomEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners({CustomEntityListener.class})
public abstract class BaseEntity {

    public abstract void setId(String id);

    public abstract String getId();
}
