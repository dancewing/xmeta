package io.xmeta.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "Entity_Permission_Role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"entity_version_id", "action", "app_role_id"})
})
public class EntityPermissionRoleEntity extends BaseEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "entity_version_id", insertable = false, updatable = false)
    private String entityVersionId;

    @Column(name = "action", insertable = false, updatable = false)
    private String action;

    @ManyToOne
    private AppRoleEntity appRole;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "entity_version_id", referencedColumnName = "entity_version_id"),
            @JoinColumn(name = "action", referencedColumnName = "action")
    })
    private EntityPermissionEntity entityPermission;

    @ManyToMany(mappedBy = "permissionRoles")
    @JsonIgnore
    private Set<EntityPermissionFieldEntity> permissionFields = new HashSet<>();

}
