package io.xmeta.graphql.domain;

import io.xmeta.graphql.model.EntityVersion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
@Table ( name ="EntityPermission" )
public class EntityPermissionEntity extends BaseEntity {

   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "action")
	private String action;

	@Column(name = "type")
	private String type;

	@ManyToOne
	private EntityVersionEntity entityVersion;

	@OneToMany(mappedBy = "permission")
	private List<EntityPermissionFieldEntity> permissionFields = new ArrayList<>();

	@OneToMany
	private List<EntityPermissionRoleEntity> permissionRoles = new ArrayList<>();



}
