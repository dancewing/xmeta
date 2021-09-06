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
@Table ( name ="Block" )
public class BlockEntity extends BaseEntity {

   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@ManyToOne
	private AppEntity app;

	@ManyToOne
	private BlockEntity parentBlock;

	@Column(name = "blockType")
	private String blockType;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "description")
	private String description;

	@Column(name = "lockedByUserId")
	private String lockedByUserId;

	@Column(name = "lockedAt")
	private ZonedDateTime lockedAt;

	@Column(name = "deletedAt")
	private ZonedDateTime deletedAt;

}
