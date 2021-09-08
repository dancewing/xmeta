package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.ActionLogEntity;
import io.xmeta.graphql.repository.ActionLogRepository;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Jeff
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
