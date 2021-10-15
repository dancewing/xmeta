package io.xmeta.web.handler;

public interface ActionInterceptor {

    /**
     * 在接受数据之前做一些数据验证等方面操作
     */
    void onRequest(Context context);

    /**
     * 在主数据处理后做的其他额外操作
     */
    void onRequestContent(Context context);
}
