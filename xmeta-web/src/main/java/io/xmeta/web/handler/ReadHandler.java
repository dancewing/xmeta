package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.web.registrar.ActionHandler;
import io.xmeta.web.registrar.EntityHandler;
import org.springframework.beans.factory.annotation.Autowired;

@MetaHandler(description = "通用查单条", supports = ActionType.READ)
public class ReadHandler implements EntityHandler {

    private MetaDataService metadataService;

    @Autowired
    public void setMetadataService(MetaDataService metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public void process(Context context) {
//        Map<String, ?> map = this.metadataService.get(context.getUuid(), (String) context.getRequest().parameters().get("id"));
//        context.setData(R.data(map));
    }
}
