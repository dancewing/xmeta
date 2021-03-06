package io.xmeta.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "Entity_Version", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"entity_id", "version_Number"})
})
public class EntityVersionEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "updated_At")
    private ZonedDateTime updatedAt;

    @Column(name = "entity_id", insertable = false, updatable = false)
    private String entityId;

    @ManyToOne
    private EntityEntity entity;

    @Column(name = "version_Number")
    private Integer versionNumber;

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
    private CommitEntity commit;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToMany(mappedBy = "entityVersions")
    @JsonIgnore
    private List<BuildEntity> builds = new ArrayList<>();
}
