package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.service.ActionLogService;
import io.xmeta.admin.model.ActionLog;
import io.xmeta.admin.model.ActionStep;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ActionStepResolverImpl implements graphql.kickstart.tools.GraphQLResolver<ActionStep> {

    private final ActionLogService actionLogService;

    public List<ActionLog> logs(ActionStep step) {
        return this.actionLogService.getLogs(step.getId());
    }
}
