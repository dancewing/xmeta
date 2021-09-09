package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.ActionLog;
import io.xmeta.graphql.model.ActionStep;
import io.xmeta.graphql.service.ActionLogService;
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
