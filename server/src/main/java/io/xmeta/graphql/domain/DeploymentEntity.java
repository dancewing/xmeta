package io.xmeta.graphql.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Data
@Table ( name ="Deployment" )
public class DeploymentEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

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
	private Date statusUpdatedAt;

}
