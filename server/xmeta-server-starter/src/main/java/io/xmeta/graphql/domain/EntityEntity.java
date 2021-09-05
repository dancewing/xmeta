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
@Table ( name ="Entity" )
public class EntityEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@Column(name = "appId")
	private String appId;

	@Column(name = "name")
	private String name;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "pluralDisplayName")
	private String pluralDisplayName;

	@Column(name = "description")
	private String description;

	@Column(name = "lockedByUserId")
	private String lockedByUserId;

	@Column(name = "lockedAt")
	private ZonedDateTime lockedAt;

	@Column(name = "deletedAt")
	private ZonedDateTime deletedAt;

}
