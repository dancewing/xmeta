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

	@Column(name = "permissionId")
	private String permissionId;

	@Column(name = "fieldPermanentId")
	private String fieldPermanentId;

	@Column(name = "entityVersionId")
	private String entityVersionId;

	@ManyToMany
	@JoinTable(name = "EntityPermissionFieldToPermissionRole",
			joinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<EntityPermissionRoleEntity> roles = new HashSet<>();

}
