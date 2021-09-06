package io.xmeta.graphql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table ( name ="Build" )
public class BuildEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "app_Id", insertable = false, updatable = false)
	private String appId;

	@ManyToOne
	private AppEntity app;

	@Column(name = "userId")
	private String userId;

	@Column(name = "version")
	private String version;

	@Column(name = "message")
	private String message;

	@Column(name = "action_id", insertable = false, updatable = false)
	private String actionId;

	@ManyToOne
	private ActionEntity action;

	@Column(name = "images")
	private String images;

	@Column(name = "containerStatusQuery")
	private String containerStatusQuery;

	@Column(name = "containerStatusUpdatedAt")
	private ZonedDateTime containerStatusUpdatedAt;

	@Column(name = "commit_id", insertable = false, updatable = false)
	private String commitId;

	@ManyToOne
	private CommitEntity commit;

	@ManyToMany(mappedBy = "builds")
	@JsonIgnore
	private Set<BlockVersionEntity> blockVersions = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "_BuildToEntityVersion",
			joinColumns = @JoinColumn(name = "A", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "B", referencedColumnName = "id"))
	private Set<BuildEntity> entityVersions = new HashSet<>();
}
