package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.core.api.R;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.web.registrar.EntityHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@MetaHandler(description = "通用新增", supports = ActionType.Create)
public class CreateHandler implements EntityHandler {

    private MetaDataService metadataService;

    @Autowired
    public void setMetadataService(MetaDataService metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public void process(Context context) {
        Map<String, Object> requestBody = context.getRequest().requestBody();
        Map<String, Object> result = metadataService.save(context.getEntityId(), requestBody);
        context.setData(R.data(result));
    }
}
