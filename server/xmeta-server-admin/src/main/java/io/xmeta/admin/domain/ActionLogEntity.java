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
@Table(name = "action_log")
public class ActionLogEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "message")
    private String message;

    @Column(name = "meta")
    @Lob
    private byte[] meta;

    @Column(name = "level")
    private String level;

    @ManyToOne
    private ActionStepEntity step;

}
