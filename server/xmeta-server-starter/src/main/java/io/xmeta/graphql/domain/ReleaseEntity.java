package io.xmeta.graphql.domain;

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
@Table(name = "Release_")
public class ReleaseEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "version_")
    private String version;

    @Column(name = "description_")
    private String description;

    @Column(name = "commit_Id")
    private String commitId;

}
