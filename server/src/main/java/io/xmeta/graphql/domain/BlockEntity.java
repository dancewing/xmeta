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
@Table ( name ="Block" )
public class BlockEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "appId")
	private String appId;

	@Column(name = "parentBlockId")
	private String parentBlockId;

	@Column(name = "blockType")
	private String blockType;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "description")
	private String description;

	@Column(name = "lockedByUserId")
	private String lockedByUserId;

	@Column(name = "lockedAt")
	private Date lockedAt;

	@Column(name = "deletedAt")
	private Date deletedAt;

}
