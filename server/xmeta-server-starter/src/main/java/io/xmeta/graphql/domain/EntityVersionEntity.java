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
@Table ( name ="EntityVersion" )
public class EntityVersionEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@Column(name = "entityId", insertable = false, updatable = false)
	private String entityId;

	@ManyToOne
	private EntityEntity entity;

	@Column(name = "versionNumber")
	private Long versionNumber;

	@Column(name = "name")
	private String name;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "pluralDisplayName")
	private String pluralDisplayName;

	@Column(name = "description")
	private String description;

	@Column(name = "commitId")
	private String commitId;

	@Column(name = "deleted")
	private Boolean deleted;

	@ManyToMany(mappedBy = "entityVersions")
	@JsonIgnore
	private Set<BuildEntity> builds = new HashSet<>();

}
