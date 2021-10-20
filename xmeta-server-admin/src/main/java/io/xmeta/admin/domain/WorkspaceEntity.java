package io.xmeta.admin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

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

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "updated_At")
    private ZonedDateTime updatedAt;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "workspace")
//    private List<UserEntity> users = new ArrayList<>();
//
//    @OneToMany(mappedBy = "workspace")
//    private List<AppEntity> apps = new ArrayList<>();

}
