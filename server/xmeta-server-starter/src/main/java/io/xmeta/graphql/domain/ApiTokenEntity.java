package io.xmeta.graphql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Entity
@Getter
@Setter
@Table(name = "api_token")
public class ApiTokenEntity extends BaseEntity {


    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "token")
    private String token;

    @Column(name = "preview_chars")
    private String previewChars;

    @Column(name = "last_access_at")
    private ZonedDateTime lastAccessAt;

}
