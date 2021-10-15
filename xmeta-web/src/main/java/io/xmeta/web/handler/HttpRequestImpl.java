package io.xmeta.web.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

public class HttpRequestImpl implements Request {

    private final HttpHeaders headers;
    private final HttpMethod method;
    private final Map<String, Object> parameters;
    private final Map<String, Object> requestBody;

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
