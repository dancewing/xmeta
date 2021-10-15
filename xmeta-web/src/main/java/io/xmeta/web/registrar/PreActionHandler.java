package io.xmeta.web.registrar;


import io.xmeta.web.handler.Context;


public interface PreActionHandler extends ActionHandler {
    void process(Context context);
}
