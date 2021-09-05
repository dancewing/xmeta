package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.EntityFieldEntity;
import io.xmeta.graphql.repository.EntityFieldRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class EntityFieldService extends BaseService<EntityFieldRepository, EntityFieldEntity, String> {

    private final EntityFieldRepository fieldRepository;

    public EntityFieldService(EntityFieldRepository fieldRepository) {
        super(fieldRepository);
        this.fieldRepository = fieldRepository;
    }
}
