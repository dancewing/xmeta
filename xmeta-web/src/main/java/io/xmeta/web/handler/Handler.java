package io.xmeta.web.handler;

@FunctionalInterface
public interface Handler<T> {

    void handle(T result);
}
