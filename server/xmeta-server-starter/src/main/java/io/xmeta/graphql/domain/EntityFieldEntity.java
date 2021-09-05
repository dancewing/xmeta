package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "entityVersionId")
	private String entityVersionId;

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
	private Long position;

	@Column(name = "unique_")
	private Boolean unique;

}
