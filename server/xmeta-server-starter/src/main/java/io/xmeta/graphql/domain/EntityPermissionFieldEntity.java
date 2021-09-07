package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
@Table ( name ="EntityPermissionField" )
public class EntityPermissionFieldEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@ManyToOne
	private EntityPermissionEntity permission;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "fieldPermanentId", referencedColumnName = "permanentId"),
			@JoinColumn(name = "entityVersionId", referencedColumnName = "entity_version_id"),
	})
	private EntityFieldEntity field;

	@Column(name = "fieldPermanentId", insertable = false, updatable = false)
	private String fieldPermanentId;

	@Column(name = "entityVersionId", insertable = false, updatable = false)
	private String entityVersionId;

	@ManyToMany
	@JoinTable(name = "EntityPermissionFieldToPermissionRole",
			joinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<EntityPermissionRoleEntity> permissionRoles = new HashSet<>();

}
