package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "UserRole", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "role_"})
})
public class UserRoleEntity extends BaseEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "createdAt")
    private ZonedDateTime createdAt;

    @Column(name = "updatedAt")
    private ZonedDateTime updatedAt;

    @ManyToOne
    private UserEntity user;

    @Column(name = "role_")
    private String role;

}
