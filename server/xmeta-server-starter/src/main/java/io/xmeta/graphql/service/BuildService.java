package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.BuildEntity;
import io.xmeta.graphql.repository.BuildRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class BuildService extends BaseService<BuildRepository, BuildEntity, String> {

    private final BuildRepository buildRepository;

    public BuildService(BuildRepository buildRepository) {
        super(buildRepository);
        this.buildRepository = buildRepository;
    }
}
