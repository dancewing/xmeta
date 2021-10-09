package io.xmeta.admin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "workspace_id"})
})
public class UserEntity extends BaseEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "updated_At")
    private ZonedDateTime updatedAt;

    @Column(name = "is_Owner")
    private Boolean isOwner = Boolean.FALSE;

    @ManyToOne
    private AccountEntity account;

    @ManyToOne
    private WorkspaceEntity workspace;

    @OneToMany(mappedBy = "user")
    private List<UserRoleEntity> userRoles = new ArrayList<>();

}
