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
@Table ( name ="ActionLog" )
public class ActionLogEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "message")
	private String message;

	@Column(name = "meta")
	private String meta;

	@Column(name = "level")
	private String level;

	@Column(name = "stepId")
	private String stepId;

}
