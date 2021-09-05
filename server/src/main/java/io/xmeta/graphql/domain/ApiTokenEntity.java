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
@Table ( name ="ApiToken" )
public class ApiTokenEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "name")
	private String name;

	@Column(name = "userId")
	private String userId;

	@Column(name = "token")
	private String token;

	@Column(name = "previewChars")
	private String previewChars;

	@Column(name = "lastAccessAt")
	private Date lastAccessAt;

}
