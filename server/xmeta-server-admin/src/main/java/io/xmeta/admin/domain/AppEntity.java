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
@Table(name = "app", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"workspace_id", "name"})
})
public class AppEntity extends BaseEntity {


    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToOne
    private WorkspaceEntity workspace;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "github_token")
    private String githubToken;

    @Column(name = "github_token_created_date")
    private ZonedDateTime githubTokenCreatedDate;

    @Column(name = "github_sync_enabled")
    private Boolean githubSyncEnabled;

    @Column(name = "github_repo")
    private String githubRepo;

    @Column(name = "github_branch")
    private String githubBranch;

    @Column(name = "github_last_sync")
    private ZonedDateTime githubLastSync;

    @Column(name = "github_last_message")
    private String githubLastMessage;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @OneToMany(mappedBy = "app")
    private List<EnvironmentEntity> environments = new ArrayList<>();

}
