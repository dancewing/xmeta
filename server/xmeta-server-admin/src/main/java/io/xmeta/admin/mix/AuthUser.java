package io.xmeta.admin.mix;

import io.xmeta.graphql.model.Account;
import io.xmeta.graphql.model.User;
import io.xmeta.graphql.model.UserRole;
import io.xmeta.graphql.model.Workspace;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuthUser extends User {
    Account account;
    Workspace workspace;
    List<UserRole> userRoles = new ArrayList<>();
}
