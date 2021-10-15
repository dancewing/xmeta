package io.xmeta.web.handler;

import org.springframework.http.HttpHeaders;

public interface Response {

    int status();

    /**
     * Reason-Phrase is intended to give a short textual description of the Status-Code.
     *
     * @return
     */
    String reason();

    Response reason(String message);

    /**
     * @return the headers in the response.
     */
    HttpHeaders headers();
}
