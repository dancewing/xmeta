package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.core.R;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.web.registrar.EntityHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@MetaHandler(description = "通用删除", supports = ActionType.Delete)
public class DeleteHandler implements EntityHandler {

    private MetaDataService metadataService;

    @Autowired
    public void setMetadataService(MetaDataService metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public void process(Context context) {
        Map<String, Object> requestBody = context.getRequest().requestBody();
        int result = metadataService.deleteById(context.getEntityId(), requestBody);
        context.setData(R.data(result));
    }
}
