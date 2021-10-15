package io.xmeta.web.handler;

import io.xmeta.web.handler.Response;
import org.springframework.http.HttpHeaders;

public class HttpResponseImpl implements Response {

    private int status;
    private String reason;
    private HttpHeaders headers;

    public HttpResponseImpl() {
    }

    public HttpResponseImpl(int status, String reason, HttpHeaders headers) {
        this.status = status;
        this.reason = reason;
        this.headers = headers;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public String reason() {
        return reason;
    }

    @Override
    public Response reason(String message) {
        this.reason = message;
        return this;
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }
}
