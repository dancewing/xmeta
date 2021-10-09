package io.xmeta.admin.service;

import io.xmeta.admin.domain.ActionLogEntity;
import io.xmeta.admin.mapper.ActionLogMapper;
import io.xmeta.admin.model.ActionLog;
import io.xmeta.admin.repository.ActionLogRepository;
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
@Transactional
@PreAuthorize("isAuthenticated()")
public class ActionLogService extends BaseService<ActionLogRepository, ActionLogEntity, String> {

    private final ActionLogRepository actionlogRepository;
    private final ActionLogMapper actionLogMapper;

    public ActionLogService(ActionLogRepository actionlogRepository, ActionLogMapper actionLogMapper) {
        super(actionlogRepository);
        this.actionlogRepository = actionlogRepository;
        this.actionLogMapper = actionLogMapper;
    }

    public List<ActionLog> getLogs(String stepId) {
        return this.actionLogMapper.toDto(this.actionlogRepository.findActionLogs(stepId));
    }
}
