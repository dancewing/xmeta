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
@Table ( name ="Account" )
public class AccountEntity extends BaseEntity {


   	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "updatedAt")
	private Date updatedAt;

	@Column(name = "email")
	private String email;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "password")
	private String password;

	@Column(name = "currentUserId")
	private String currentUserId;

	@Column(name = "githubId")
	private String githubId;

}
