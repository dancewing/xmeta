package io.xmeta.admin.domain;

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
@Table(name = "App_Role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"app_id", "display_name"}),
        @UniqueConstraint(columnNames = {"app_id", "name"})
})
public class AppRoleEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "app_id")
    private AppEntity app;

    @Column(name = "name")
    private String name;

    @Column(name = "display_Name")
    private String displayName;

    @Column(name = "description")
    private String description;

}
