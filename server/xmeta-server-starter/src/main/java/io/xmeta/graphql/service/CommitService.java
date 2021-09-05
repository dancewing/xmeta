package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.CommitEntity;
import io.xmeta.graphql.repository.CommitRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class CommitService extends BaseService<CommitRepository, CommitEntity, String> {

    private final CommitRepository commitRepository;

    public CommitService(CommitRepository commitRepository) {
        super(commitRepository);
        this.commitRepository = commitRepository;
    }
}
