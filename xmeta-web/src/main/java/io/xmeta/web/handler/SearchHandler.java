package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.web.registrar.EntityHandler;

@MetaHandler(description = "通用查列表", supports = ActionType.Search)
public class SearchHandler implements EntityHandler {
    @Override
    public void process(Context context) {

    }
}
