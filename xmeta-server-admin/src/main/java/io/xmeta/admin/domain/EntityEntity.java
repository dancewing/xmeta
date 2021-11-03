package io.xmeta.admin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "Entity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"app_id", "display_name"}),
        @UniqueConstraint(columnNames = {"app_id", "name"})
})
public class EntityEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToOne
    private AppEntity app;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "table_")
    private String table;

    @NotNull
    @Column(name = "display_Name")
    private String displayName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private UserEntity lockedByUser;

    @Column(name = "locked_at")
    private ZonedDateTime lockedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

}
