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
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table ( name ="Deployment" )
public class DeploymentEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "userId")
	private String userId;

	@Column(name = "buildId")
	private String buildId;

	@Column(name = "environmentId")
	private String environmentId;

	@Column(name = "status")
	private String status;

	@Column(name = "message")
	private String message;

	@Column(name = "actionId")
	private String actionId;

	@Column(name = "statusQuery")
	private String statusQuery;

	@Column(name = "statusUpdatedAt")
	private ZonedDateTime statusUpdatedAt;

}
