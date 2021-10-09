package io.xmeta.admin.service;

import io.xmeta.admin.domain.ActionStepEntity;
import io.xmeta.admin.repository.ActionStepRepository;
import io.xmeta.admin.mapper.ActionStepMapper;
import io.xmeta.graphql.model.ActionStep;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@PreAuthorize("isAuthenticated()")
@Transactional
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
