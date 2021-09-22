package io.xmeta.graphql.mix;


import java.util.List;


public class WorkspaceDomain {
    private String id;
    private String name;
    private List<AppDomain> apps;

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

    public List<AppDomain> getApps() {
        return apps;
    }

    public void setApps(List<AppDomain> apps) {
        this.apps = apps;
    }
}
