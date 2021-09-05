package io.xmeta.graphql.domain;

import lombok.Data;
import javax.persistence.*;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Data
@Table ( name ="EntityPermission" )
public class EntityPermissionEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "entityVersionId")
	private String entityVersionId;

	@Column(name = "action")
	private String action;

	@Column(name = "type")
	private String type;

}
