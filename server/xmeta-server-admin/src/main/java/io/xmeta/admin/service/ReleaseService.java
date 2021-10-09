package io.xmeta.admin.service;

import io.xmeta.admin.domain.ReleaseEntity;
import io.xmeta.admin.repository.ReleaseRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@PreAuthorize("isAuthenticated()")
@Transactional(readOnly = true)
public class ReleaseService extends BaseService<ReleaseRepository, ReleaseEntity, String> {

    private final ReleaseRepository releaseRepository;

    public ReleaseService(ReleaseRepository releaseRepository) {
        super(releaseRepository);
        this.releaseRepository = releaseRepository;
    }
}
