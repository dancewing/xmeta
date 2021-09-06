package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.AppEntity;
import io.xmeta.graphql.domain.UserEntity;
import io.xmeta.graphql.model.Commit;
import io.xmeta.graphql.model.CommitCreateInput;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.springframework.stereotype.Service;
import io.xmeta.graphql.domain.CommitEntity;
import io.xmeta.graphql.repository.CommitRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class CommitService extends BaseService<CommitRepository, CommitEntity, String> {

    private final CommitRepository commitRepository;

    public CommitService(CommitRepository commitRepository) {
        super(commitRepository);
        this.commitRepository = commitRepository;
    }

    @Transactional(readOnly = false)
    public Commit commit(CommitCreateInput data) {
        CommitEntity commit = new CommitEntity();
        AppEntity app = new AppEntity();
        app.setId(data.getApp().getConnect().getId());
        commit.setApp(app);
        UserEntity user = new UserEntity();
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        user.setId(authUserDetail.getUserId());
        commit.setUser(user);
        commit.setMessage(data.getMessage());
        this.commitRepository.save(commit);
        return null;
    }
}
