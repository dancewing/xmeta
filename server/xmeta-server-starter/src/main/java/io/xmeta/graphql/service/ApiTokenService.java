package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.ApiTokenEntity;
import io.xmeta.graphql.repository.ApiTokenRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class ApiTokenService extends BaseService<ApiTokenRepository, ApiTokenEntity, String> {

    private final ApiTokenRepository apitokenRepository;

    public ApiTokenService(ApiTokenRepository apitokenRepository) {
        super(apitokenRepository);
        this.apitokenRepository = apitokenRepository;
    }
}
