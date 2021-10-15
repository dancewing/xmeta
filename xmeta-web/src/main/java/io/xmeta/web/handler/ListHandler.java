package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.web.registrar.ActionHandler;
import io.xmeta.web.registrar.EntityHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @projectName xmetadata
 * @class: ListHandler
 * @author: DuChenYang
 * @datetime: 2021/5/21 11:17
 * @description:
 */
@MetaHandler(description = "通用查列表", supports = ActionType.LIST)
public class ListHandler implements EntityHandler {

    private MetaDataService metadataService;

    @Autowired
    public void setMetadataService(MetaDataService metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public void process(Context context) {

    }
}
