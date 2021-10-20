package io.xmeta.admin.resolver.impl;

import io.xmeta.admin.model.*;
import io.xmeta.admin.resolver.AppResolver;
import io.xmeta.admin.service.BuildService;
import io.xmeta.admin.service.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppResolverImpl implements AppResolver {

    private final BuildService buildService;

    private final EntityService entityService;

    @Override
    public List<Entity> entities(App app, EntityWhereInput where, EntityOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityService.entities(app, where, orderBy, skip, take);
    }

    @Override
    public List<Build> builds(App app, BuildWhereInput where, BuildOrderByInput orderBy, Integer take, Integer skip) {
        return this.buildService.builds(app, null, where, orderBy, take, skip);
    }
}
