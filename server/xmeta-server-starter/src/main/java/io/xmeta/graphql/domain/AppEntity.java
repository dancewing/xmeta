package io.xmeta.graphql.domain;

import io.xmeta.graphql.model.Environment;
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
@Table ( name ="App" )
public class AppEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

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
	private ZonedDateTime githubTokenCreatedDate;

	@Column(name = "githubSyncEnabled")
	private Boolean githubSyncEnabled;

	@Column(name = "githubRepo")
	private String githubRepo;

	@Column(name = "githubBranch")
	private String githubBranch;

	@Column(name = "githubLastSync")
	private ZonedDateTime githubLastSync;

	@Column(name = "githubLastMessage")
	private String githubLastMessage;

	@Column(name = "deletedAt")
	private ZonedDateTime deletedAt;

	@OneToMany(mappedBy = "app")
	private List<EnvironmentEntity> environments = new ArrayList<>();

}
