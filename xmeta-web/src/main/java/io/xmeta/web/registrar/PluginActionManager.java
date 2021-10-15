package io.xmeta.web.registrar;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PluginActionManager {

    private ConcurrentMap<ActionType, EntityHandler> entityHandlers;
    private ConcurrentMap<ActionType, List<PreActionHandler>> preActionHandlers;
    private ConcurrentMap<ActionType, List<PostActionHandler>> postActionHandlers;
    private ConcurrentMap<String, ExportHandler> exportHandlers;


    private final ApplicationContext applicationContext;

    public PluginActionManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        loadPostActionHandlers();
        loadPreActionHandlers();
        loadEntityHandlers();
    }

    public List<PostActionHandler> getPostActionHandler(ActionType action) {
        return postActionHandlers.get(action);
    }


    public List<PreActionHandler> getPreActionHandler(ActionType action) {
        return preActionHandlers.get(action);
    }

    public EntityHandler getEntityHandler(ActionType actionType) {
        if (entityHandlers.containsKey(actionType)) {
            return entityHandlers.get(actionType);
        }
        return null;
    }

    private void loadEntityHandlers() {
        entityHandlers = new ConcurrentHashMap<>();
        Map<String, EntityHandler> actionHandlerBeans = applicationContext.getBeansOfType(EntityHandler.class);
        actionHandlerBeans.forEach((key, value) -> {
            Class<? extends ActionHandler> aClass = value.getClass();
            MetaHandler metaHandler = aClass.getDeclaredAnnotation(MetaHandler.class);
            if (ObjectUtils.isNotEmpty(metaHandler)) {
                if (ArrayUtils.isNotEmpty(metaHandler.supports())) {
                    ActionType[] types = metaHandler.supports();
                    for (ActionType type : types) {
                        entityHandlers.put(type, value);
                    }
                }
            }
        });
    }


    private void loadPostActionHandlers() {
        postActionHandlers = new ConcurrentHashMap<>();
        Map<String, PostActionHandler> actionHandlerBeans = applicationContext.getBeansOfType(PostActionHandler.class);

        actionHandlerBeans.values().forEach( postActionHandler -> {
            MetaHandler metaHandler = postActionHandler.getClass().getDeclaredAnnotation(MetaHandler.class);
            if (ObjectUtils.isNotEmpty(metaHandler)) {
                if (ArrayUtils.isNotEmpty(metaHandler.supports())) {
                    ActionType[] types = metaHandler.supports();
                    for (ActionType type : types) {
                        List<PostActionHandler> handlers = this.postActionHandlers.computeIfAbsent(type, k -> new ArrayList());
                        handlers.add(postActionHandler);
                    }
                }
            }
        });
    }

    private void loadPreActionHandlers() {
        preActionHandlers = new ConcurrentHashMap<>();

        Map<String, PreActionHandler> actionHandlerBeans = applicationContext.getBeansOfType(PreActionHandler.class);

        actionHandlerBeans.values().forEach( postActionHandler -> {
            MetaHandler metaHandler = postActionHandler.getClass().getDeclaredAnnotation(MetaHandler.class);
            if (ObjectUtils.isNotEmpty(metaHandler)) {
                if (ArrayUtils.isNotEmpty(metaHandler.supports())) {
                    ActionType[] types = metaHandler.supports();
                    for (ActionType type : types) {
                        List<PreActionHandler> handlers = this.preActionHandlers.computeIfAbsent(type, k -> new ArrayList());
                        handlers.add(postActionHandler);
                    }
                }
            }
        });
    }
}
