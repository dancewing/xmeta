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
@Table ( name ="Entity" )
public class EntityEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@ManyToOne
	private AppEntity app;

	@Column(name = "name")
	private String name;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "pluralDisplayName")
	private String pluralDisplayName;

	@Column(name = "description")
	private String description;

	@ManyToOne
	private UserEntity lockedByUser;

	@Column(name = "lockedAt")
	private ZonedDateTime lockedAt;

	@Column(name = "deletedAt")
	private ZonedDateTime deletedAt;


//	private List<EntityPermissionEntity>  permissions = new ArrayList<>();

}
