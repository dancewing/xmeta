package io.xmeta.web.handler.internal;

import io.xmeta.web.handler.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

public class HttpRequestImpl implements Request {

    private HttpHeaders headers;
    private HttpMethod method;
    private Map<String, Object> parameters;
    private Map<String, Object> requestBody;

    public HttpRequestImpl(HttpHeaders headers, HttpMethod method, Map<String, Object> parameters, Map<String, Object> requestBody) {
        this.headers = headers;
        this.method = method;
        this.parameters = parameters;
        this.requestBody = requestBody;
    }

    @Override
    public Map<String, Object> parameters() {
        return parameters;
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public Map<String, Object> requestBody() {
        return requestBody;
    }

    @Override
    public String rawMethod() {
        if (this.method != null) {
            return this.method.name();
        }
        return "OTHER";
    }

}
