package io.xmeta.graphql.domain;

import io.xmeta.graphql.model.Commit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
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
@Table ( name ="BlockVersion" )
public class BlockVersionEntity extends BaseEntity {


   	@Id
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "createdAt")
	private ZonedDateTime createdAt;

	@Column(name = "updatedAt")
	private ZonedDateTime updatedAt;

	@ManyToOne
	private BlockEntity block;

	@Column(name = "versionNumber")
	private Long versionNumber;

	@Column(name = "inputParameters")
	private String inputParameters;

	@Column(name = "outputParameters")
	private String outputParameters;

	@Column(name = "settings")
	@Lob
	private String settings;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "description")
	private String description;

	@ManyToOne
	private CommitEntity commit;

	@Column(name = "deleted")
	private Boolean deleted;

	@ManyToMany
	@JoinTable(name = "BlockVersionToBuild",
			joinColumns = @JoinColumn(name = "A", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "B", referencedColumnName = "id"))
	private Set<BuildEntity> builds = new HashSet<>();

}
