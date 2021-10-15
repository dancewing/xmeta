package io.xmeta.web.handler.internal;


import io.xmeta.web.handler.ActionInterceptor;
import io.xmeta.web.handler.Context;

public class EmptyActionInterceptor implements ActionInterceptor {

    @Override
    public void onRequest(Context context) {

    }

    @Override
    public void onRequestContent(Context context) {

    }
}
