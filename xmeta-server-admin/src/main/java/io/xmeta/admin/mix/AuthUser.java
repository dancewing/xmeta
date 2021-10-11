package io.xmeta.admin.mix;

import io.xmeta.admin.model.Account;
import io.xmeta.admin.model.User;
import io.xmeta.admin.model.UserRole;
import io.xmeta.admin.model.Workspace;
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
