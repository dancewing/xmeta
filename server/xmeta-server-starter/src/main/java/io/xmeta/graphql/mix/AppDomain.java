package io.xmeta.graphql.mix;

import java.util.List;

public class AppDomain {
    private String id;
    private String name;
    private List<EntityDomain> entities;

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

    public List<EntityDomain> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDomain> entities) {
        this.entities = entities;
    }
}
