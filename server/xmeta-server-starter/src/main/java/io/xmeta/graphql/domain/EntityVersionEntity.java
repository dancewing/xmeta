package io.xmeta.graphql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	@Column(name = "entity_id", insertable = false, updatable = false)
	private String entityId;

	@ManyToOne
	private EntityEntity entity;

	@Column(name = "versionNumber")
	private Integer versionNumber;

	@Column(name = "name")
	private String name;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "pluralDisplayName")
	private String pluralDisplayName;

	@Column(name = "description")
	private String description;

	@ManyToOne
	private CommitEntity commit;

	@Column(name = "deleted")
	private Boolean deleted;

	@ManyToMany(mappedBy = "entityVersions")
	@JsonIgnore
	private List<BuildEntity> builds = new ArrayList<>();

	@OneToMany(mappedBy = "entityVersion")
	private List<EntityFieldEntity> fields = new ArrayList<>();

	@OneToMany(mappedBy = "entityVersion")
	private List<EntityPermissionEntity> permissions = new ArrayList<>();
}
