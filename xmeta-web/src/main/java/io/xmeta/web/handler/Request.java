package io.xmeta.web.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

public interface Request {

    /**
     * @return the query parameters in the request
     */
    Map<String, Object> parameters();

    /**
     * @return the headers in the request.
     */
    HttpHeaders headers();

    /**
     * @return the HTTP method for the request.
     */
    HttpMethod method();

    String rawMethod();

    Map<String, Object> requestBody();

    static Request of(HttpHeaders headers, HttpMethod method, Map<String, Object> parameters, Map<String, Object> requestBody) {
        return new HttpRequestImpl(headers, method, parameters, requestBody);
    }

}
