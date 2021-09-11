package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Action;
import io.xmeta.graphql.model.ActionStep;
import io.xmeta.graphql.service.ActionStepService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ActionResolverImpl implements graphql.kickstart.tools.GraphQLResolver<Action> {

    private final ActionStepService actionStepService;

    public List<ActionStep> steps(Action action) {
        return this.actionStepService.getSteps(action.getId());
    }

}
