package io.xmeta.admin.mix;

import io.xmeta.core.domain.Entity;

import java.util.List;

public class AppDomain {
    private String id;
    private String name;
    private List<Entity> entities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
