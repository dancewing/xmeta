package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.core.R;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.web.registrar.EntityHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @projectName xmetadata
 * @class: UpdateHandler
 * @author: DuChenYang
 * @datetime: 2021/5/21 11:18
 * @description:
 */
@MetaHandler(description = "通用更新", supports = ActionType.UPDATE)
public class UpdateHandler implements EntityHandler {

    private MetaDataService metadataService;

    @Autowired
    public void setMetadataService(MetaDataService metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public void process(Context context) {
        Map<String, Object> requestBody = context.getRequest().requestBody();
        Map<String, Object> result = metadataService.update(context.getEntityId(), requestBody);
        context.setData(R.data(result));
    }
}
