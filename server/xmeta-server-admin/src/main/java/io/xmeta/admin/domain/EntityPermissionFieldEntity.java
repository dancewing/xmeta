package io.xmeta.admin.domain;

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
@Table(name = "Entity_Permission_Field", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"permission_id", "field_permanent_id"})
})
public class EntityPermissionFieldEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @ManyToOne
    private EntityPermissionEntity permission;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "field_permanent_id", referencedColumnName = "permanent_id"),
            @JoinColumn(name = "entity_version_id", referencedColumnName = "entity_version_id"),
    })
    private EntityFieldEntity field;

    @Column(name = "field_permanent_id", insertable = false, updatable = false)
    private String fieldPermanentId;

    @Column(name = "entity_version_id", insertable = false, updatable = false)
    private String entityVersionId;

    @ManyToMany
    @JoinTable(name = "entity_permission_field_role",
            joinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<EntityPermissionRoleEntity> permissionRoles = new HashSet<>();

}
