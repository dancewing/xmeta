package io.xmeta.web.service.impl;

import io.xmeta.core.R;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.service.MetaEntityService;
import io.xmeta.web.ApiException;
import io.xmeta.web.handler.ActionInterceptor;
import io.xmeta.web.handler.Context;
import io.xmeta.web.handler.Response;
import io.xmeta.web.handler.HttpResponseImpl;
import io.xmeta.web.registrar.EntityHandler;
import io.xmeta.web.registrar.PluginActionManager;
import io.xmeta.web.registrar.PostActionHandler;
import io.xmeta.web.service.RestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RestServiceImpl implements RestService {
    private final MetaEntityService metaEntityService;
    private final PluginActionManager pluginActionManager;
    private final ActionInterceptor actionInterceptor;


    public RestServiceImpl(MetaEntityService metaEntityService, PluginActionManager pluginActionManager, ActionInterceptor actionInterceptor) {
        this.metaEntityService = metaEntityService;
        this.pluginActionManager = pluginActionManager;
        this.actionInterceptor = actionInterceptor;
    }

    /**
     *
     * @param context 需要处理的上下文数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(Context context) {
        Assert.notNull(context, "context can't be null");
        Entity entity = this.metaEntityService.getEntity(context.getEntityId());
        EntityHandler entityHandler = pluginActionManager.getEntityHandler(context.getType());

        if (entity == null) {
            throw new ApiException("uuid 不存在");
        }
        if (entityHandler == null) {
            throw new ApiException("action 不存在");
        }

        List<PostActionHandler> postActionHandler = pluginActionManager.getPostActionHandler(context.getType());

        actionInterceptor.onRequest(context);

        entityHandler.process(context);

        R result = context.getData();
        if (result == null || result.getCode() != 200) {
            throw new ApiException("处理异常");
        }

        Response response = new HttpResponseImpl();
        context.setResponse(response);

        actionInterceptor.onRequestContent(context);
    }
}
