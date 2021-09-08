package io.xmeta.graphql.domain;

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
@Table(name = "Workspace")
public class WorkspaceEntity extends BaseEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "createdAt")
    private ZonedDateTime createdAt;

    @Column(name = "updatedAt")
    private ZonedDateTime updatedAt;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "workspace")
    private List<UserEntity> users = new ArrayList<>();

    @OneToMany(mappedBy = "workspace")
    private List<AppEntity> apps = new ArrayList<>();

}
