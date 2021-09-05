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
@Table ( name ="App" )
public class AppEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "workspaceId")
	private String workspaceId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "color")
	private String color;

	@Column(name = "githubToken")
	private String githubToken;

	@Column(name = "githubTokenCreatedDate")
	private Date githubTokenCreatedDate;

	@Column(name = "githubSyncEnabled")
	private Boolean githubSyncEnabled;

	@Column(name = "githubRepo")
	private String githubRepo;

	@Column(name = "githubBranch")
	private String githubBranch;

	@Column(name = "githubLastSync")
	private Date githubLastSync;

	@Column(name = "githubLastMessage")
	private String githubLastMessage;

	@Column(name = "deletedAt")
	private Date deletedAt;

}
