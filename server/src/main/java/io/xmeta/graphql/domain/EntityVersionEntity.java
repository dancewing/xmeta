package io.xmeta.graphql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Data
@Table ( name ="EntityVersion" )
public class EntityVersionEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "entityId")
	private String entityId;

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
