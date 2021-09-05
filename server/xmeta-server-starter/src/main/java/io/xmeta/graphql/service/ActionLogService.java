package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.ActionLogEntity;
import io.xmeta.graphql.repository.ActionLogRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class ActionLogService extends BaseService<ActionLogRepository, ActionLogEntity, String> {

    private final ActionLogRepository actionlogRepository;

    public ActionLogService(ActionLogRepository actionlogRepository) {
        super(actionlogRepository);
        this.actionlogRepository = actionlogRepository;
    }
}
