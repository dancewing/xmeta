package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.ActionStepEntity;
import io.xmeta.graphql.repository.ActionStepRepository;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
public class ActionStepService extends BaseService<ActionStepRepository, ActionStepEntity, String> {

    private final ActionStepRepository actionstepRepository;

    public ActionStepService(ActionStepRepository actionstepRepository) {
        super(actionstepRepository);
        this.actionstepRepository = actionstepRepository;
    }
}
