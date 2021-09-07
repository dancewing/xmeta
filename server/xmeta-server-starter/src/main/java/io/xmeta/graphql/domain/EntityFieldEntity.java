package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table ( name ="EntityField" )
public class EntityFieldEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@ManyToOne
	private EntityVersionEntity entityVersion;

	@Column(name = "permanentId")
	private String permanentId;

	@Column(name = "name")
	private String name;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "dataType")
	private String dataType;

	@Column(name = "properties")
	private String properties;

	@Column(name = "required_")
	private Boolean required;

	@Column(name = "searchable")
	private Boolean searchable;

	@Column(name = "description")
	private String description;

	@Column(name = "position_")
	private Integer position;

	@Column(name = "unique_")
	private Boolean unique;

}
