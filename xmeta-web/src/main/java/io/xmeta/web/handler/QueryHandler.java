package io.xmeta.web.handler;

import io.xmeta.core.ActionType;
import io.xmeta.core.MetaHandler;
import io.xmeta.core.api.R;
import io.xmeta.core.service.MetaDataService;
import io.xmeta.web.registrar.EntityHandler;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@MetaHandler(description = "通用查列表", supports = ActionType.Query)
public class QueryHandler implements EntityHandler {

    private MetaDataService metadataService;

    @Autowired
    public void setMetadataService(MetaDataService metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public void process(Context context) {

        Map<String, Object> requestBody = context.getRequest().requestBody();
        Map<String, Object> parameters = context.getRequest().parameters();
        Integer page = MapUtils.getInteger(parameters, "page", null);
        Integer pageSize = MapUtils.getInteger(parameters, "size", null);

        List<Map<String, Object>> result = metadataService.query(context.getEntityId(), page, pageSize);
        context.setData(R.data(result));
    }
}
