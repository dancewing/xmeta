package io.xmeta.graphql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Data
@Table ( name ="EntityPermissionRole" )
public class EntityPermissionRoleEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "entityVersionId")
	private String entityVersionId;

	@Column(name = "action")
	private String action;

	@Column(name = "appRoleId")
	private String appRoleId;

	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private Set<EntityPermissionFieldEntity> fields = new HashSet<>();

}
