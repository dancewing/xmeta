package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table ( name ="ActionStep" )
public class ActionStepEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "message")
	private String message;

	@Column(name = "status")
	private String status;

	@Column(name = "completedAt")
	private ZonedDateTime completedAt;

	@Column(name = "name")
	private String name;

	@ManyToOne
	private ActionEntity action;

	@OneToMany(mappedBy = "step")
	private List<ActionLogEntity> logs = new ArrayList<>();

}
