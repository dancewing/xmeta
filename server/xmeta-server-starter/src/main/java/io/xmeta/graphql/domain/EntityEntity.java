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
@Table(name = "Entity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"app_id", "display_name"}),
        @UniqueConstraint(columnNames = {"app_id", "name"}),
        @UniqueConstraint(columnNames = {"app_id", "plural_display_name"})
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

    @Column(name = "name")
    private String name;

    @Column(name = "display_Name")
    private String displayName;

    @Column(name = "plural_display_name")
    private String pluralDisplayName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private UserEntity lockedByUser;

    @Column(name = "locked_at")
    private ZonedDateTime lockedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @OneToMany(mappedBy = "entity")
    @OrderBy("versionNumber desc")
    private List<EntityVersionEntity> versions = new ArrayList<>();

}
