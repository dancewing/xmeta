package io.xmeta.admin.service;

import io.xmeta.admin.domain.ApiTokenEntity;
import io.xmeta.admin.repository.ApiTokenRepository;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Jeff
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
