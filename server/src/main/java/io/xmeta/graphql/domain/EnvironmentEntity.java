package io.xmeta.graphql.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Entity
@Data
@Table ( name ="Environment" )
public class EnvironmentEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "appId")
	private String appId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "address")
	private String address;

}
