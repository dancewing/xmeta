package io.xmeta.admin.mix;

import java.util.List;

public class EntityDomain {
    private String id;
    private String name;
    private String displayName;
    private String pluralDisplayName;
    private Integer versionNumber;
    private List<FieldDomain> fields;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPluralDisplayName() {
        return pluralDisplayName;
    }

    public void setPluralDisplayName(String pluralDisplayName) {
        this.pluralDisplayName = pluralDisplayName;
    }

    public List<FieldDomain> getFields() {
        return fields;
    }

    public void setFields(List<FieldDomain> fields) {
        this.fields = fields;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }
}
