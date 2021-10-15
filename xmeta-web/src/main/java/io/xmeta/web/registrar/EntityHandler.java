package io.xmeta.web.registrar;

import io.xmeta.web.handler.Context;

public interface EntityHandler extends ActionHandler {
    void process(Context context);
}
