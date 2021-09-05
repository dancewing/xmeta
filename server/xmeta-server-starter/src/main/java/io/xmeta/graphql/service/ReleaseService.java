package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.ReleaseEntity;
import io.xmeta.graphql.repository.ReleaseRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class ReleaseService extends BaseService<ReleaseRepository, ReleaseEntity, String> {

    private final ReleaseRepository releaseRepository;

    public ReleaseService(ReleaseRepository releaseRepository) {
        super(releaseRepository);
        this.releaseRepository = releaseRepository;
    }
}
