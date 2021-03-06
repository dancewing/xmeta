package io.xmeta.admin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "entity_field",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"entity_version_id", "display_name"}),
                @UniqueConstraint(columnNames = {"entity_version_id", "name"}),
        })
public class EntityFieldEntity extends BaseEntity {

    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_At")
    private ZonedDateTime createdAt;

    @Column(name = "updated_At")
    private ZonedDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "entity_version_id")
    private EntityVersionEntity entityVersion;

    /**
     * 这个字段必须要要加，负责会报找不到 'entity_version_id' 的错误信息
     */
    @Column(name = "entity_version_id", insertable = false, updatable = false)
    private String entityVersionId;

    @Column(name = "permanent_Id")
    private String permanentId;

    @Column(name = "name")
    private String name;

    @Column(name = "column_")
    private String column;

    @Column(name = "java_type")
    private String javaType;

    @Column(name = "input_type")
    private String inputType;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "data_Type")
    private String dataType;

    @Column(name = "properties")
    private byte[] properties;

    @Column(name = "required_")
    private Boolean required;

    @Column(name = "searchable")
    private Boolean searchable;

    @Column(name = "description")
    private String description;

    @Column(name = "position_")
    private Integer position;

    @Column(name = "unique_")
    private Boolean unique;

}
