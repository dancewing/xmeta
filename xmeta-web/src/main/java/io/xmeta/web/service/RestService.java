package io.xmeta.web.service;

import io.xmeta.web.handler.Context;

/**
 * @author jeff_qian
 */
public interface RestService {
    /**
     * 处理请求
     *
     * @param context 需要处理的上下文数据
     */
    void process(Context context);
}
