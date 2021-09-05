package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "message")
	private String message;

	@Column(name = "status")
	private String status;

	@Column(name = "completedAt")
	private Date completedAt;

	@Column(name = "actionId")
	private String actionId;

	@Column(name = "name")
	private String name;

}
