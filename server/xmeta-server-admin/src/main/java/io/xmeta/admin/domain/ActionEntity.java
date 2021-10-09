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
@Table(name = "Action")
public class ActionEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "action")
    private List<ActionStepEntity> steps = new ArrayList<>();

}
