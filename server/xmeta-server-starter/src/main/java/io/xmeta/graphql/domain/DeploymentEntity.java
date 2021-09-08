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
@Table(name = "Deployment")
public class DeploymentEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "createdAt")
    private ZonedDateTime createdAt;

    @Column(name = "userId")
    private String userId;

    @Column(name = "build_id", insertable = false, updatable = false)
    private String buildId;

    @ManyToOne
    private BuildEntity build;

    @Column(name = "environment_Id", insertable = false, updatable = false)
    private String environmentId;

    @ManyToOne
    private EnvironmentEntity environment;

    @Column(name = "status")
    private String status;

    @Column(name = "message")
    private String message;

    @Column(name = "action_Id", insertable = false, updatable = false)
    private String actionId;

    @ManyToOne
    private ActionEntity action;

    @Column(name = "statusQuery")
    private String statusQuery;

    @Column(name = "statusUpdatedAt")
    private ZonedDateTime statusUpdatedAt;

}
