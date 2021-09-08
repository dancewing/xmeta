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
@Table(name = "Account")
public class AccountEntity extends BaseEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToOne
    private UserEntity currentUser;

    @Column
    private String githubId;

}
