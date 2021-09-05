package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.ActionEntity;
import io.xmeta.graphql.repository.ActionRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class ActionService extends BaseService<ActionRepository, ActionEntity, String> {

    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        super(actionRepository);
        this.actionRepository = actionRepository;
    }
}
