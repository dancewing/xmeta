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
@Table(name = "Environment")
public class EnvironmentEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "updated_At")
    private ZonedDateTime updatedAt;

    @Column(name = "app_Id", insertable = false, updatable = false)
    private String appId;

    @Column(name = "database_type")
    private String databaseType;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "user")
    private String user;

    @Column(name = "password")
    private String password;

    @ManyToOne
    private AppEntity app;

}
