package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.api.R;

public class Context {

    private Request request;
    private Response response;
    private String entityId;
    private ActionType type;

    private R data;

    public Context(Request request, String entityId, ActionType type) {
        this.request = request;
        this.entityId = entityId;
        this.type = type;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getEntityId() {
        return entityId;
    }

    public ActionType getType() {
        return type;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }
}
