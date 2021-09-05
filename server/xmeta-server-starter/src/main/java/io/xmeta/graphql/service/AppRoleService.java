package io.xmeta.graphql.service;

import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.AppRoleEntity;
import io.xmeta.graphql.repository.AppRoleRepository;

/**
 * @Description  
 * @Author  Jeff
 * @Date 2021-09-05 
 */

@Service
public class AppRoleService extends BaseService<AppRoleRepository, AppRoleEntity, String> {

    private final AppRoleRepository approleRepository;

    public AppRoleService(AppRoleRepository approleRepository) {
        super(approleRepository);
        this.approleRepository = approleRepository;
    }
}
