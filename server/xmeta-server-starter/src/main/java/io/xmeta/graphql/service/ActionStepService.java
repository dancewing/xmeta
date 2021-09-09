package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.ActionStepEntity;
import io.xmeta.graphql.mapper.ActionStepMapper;
import io.xmeta.graphql.model.ActionStep;
import io.xmeta.graphql.repository.ActionStepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
public class ActionStepService extends BaseService<ActionStepRepository, ActionStepEntity, String> {

    private final ActionStepRepository actionstepRepository;
    private final ActionStepMapper actionStepMapper;

    public ActionStepService(ActionStepRepository actionstepRepository, ActionStepMapper actionStepMapper) {
        super(actionstepRepository);
        this.actionstepRepository = actionstepRepository;
        this.actionStepMapper = actionStepMapper;
    }

    public List<ActionStep> getSteps(String actionId) {
        return this.actionStepMapper.toDto(this.actionstepRepository.findActionSteps(actionId));
    }
}
