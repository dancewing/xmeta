package io.xmeta.web;

import io.xmeta.core.MetaException;

public class ApiException extends MetaException {

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
