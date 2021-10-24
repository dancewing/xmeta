package io.xmeta.admin.model;

public enum Role {

    Admin("Admin"),
    User("User"),
    OrganizationAdmin("OrganizationAdmin"),
    ProjectAdmin("ProjectAdmin");

    private final String graphqlName;

    private Role(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
