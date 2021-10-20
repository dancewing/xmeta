package io.xmeta.admin.service;

import io.xmeta.admin.domain.ActionEntity;
import io.xmeta.admin.repository.ActionRepository;
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
@Transactional
public class ActionService extends BaseService<ActionRepository, ActionEntity, String> {

    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        super(actionRepository);
        this.actionRepository = actionRepository;
    }
}
