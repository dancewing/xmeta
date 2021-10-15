package io.xmeta.web.registrar;

import io.xmeta.core.ActionType;
import lombok.Data;

@Data
public class MetaHandlerData {
    private String description;
    private ActionType actionType;
}
