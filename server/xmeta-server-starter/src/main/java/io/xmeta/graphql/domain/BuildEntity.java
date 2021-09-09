package io.xmeta.graphql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "Build", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"app_id", "version"})
})
public class BuildEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "createdAt")
    private ZonedDateTime createdAt;

    @ManyToOne
    private AppEntity app;

    @ManyToOne
    private UserEntity createdBy;

    @Column(name = "version")
    private String version;

    @Column(name = "message")
    private String message;

    @ManyToOne
    private ActionEntity action;

    @Column(name = "images")
    @Lob
    private String images;

    @Column
    private String archiveURI = "/generated-apps/xxxxxx.zip";

    @Column(name = "container_status_query")
    private String containerStatusQuery;

    @Column(name = "container_status_updated_at")
    private ZonedDateTime containerStatusUpdatedAt;

    @ManyToOne
    private CommitEntity commit;

    @ManyToMany(mappedBy = "builds")
    @JsonIgnore
    private Set<BlockVersionEntity> blockVersions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "_BuildToEntityVersion",
            joinColumns = @JoinColumn(name = "build_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "version_id", referencedColumnName = "id"))
    private Set<EntityVersionEntity> entityVersions = new HashSet<>();
}
