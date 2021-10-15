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

}
