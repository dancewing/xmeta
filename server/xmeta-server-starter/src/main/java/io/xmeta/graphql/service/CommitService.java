package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.CommitMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.repository.CommitRepository;
import io.xmeta.security.AuthUserDetail;
import io.xmeta.security.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
public class CommitService extends BaseService<CommitRepository, CommitEntity, String> {

    private final CommitRepository commitRepository;
    private final CommitMapper commitMapper;

    public CommitService(CommitRepository commitRepository, CommitMapper commitMapper) {
        super(commitRepository);
        this.commitRepository = commitRepository;
        this.commitMapper = commitMapper;
    }

    public Commit getCommit(String id) {
        return this.commitMapper.toDto(this.commitRepository.getById(id));
    }

    @Transactional
    public Commit createCommit(CommitCreateInput data) {
        CommitEntity commit = new CommitEntity();
        AppEntity app = new AppEntity();
        app.setId(data.getApp().getConnect().getId());
        commit.setApp(app);
        UserEntity user = new UserEntity();
        AuthUserDetail authUserDetail = SecurityUtils.getAuthUser();
        user.setId(authUserDetail.getUserId());
        commit.setUser(user);
        commit.setMessage(data.getMessage());
        commit.setCreatedAt(ZonedDateTime.now());
        this.commitRepository.saveAndFlush(commit);
        return this.commitMapper.toDto(commit);
    }

    public List<Commit> commits(CommitWhereInput where, CommitOrderByInput orderBy, CommitWhereUniqueInput cursor, Integer take, Integer skip) {
        Specification<CommitEntity> specification = Specification.where(null);
        Specification<CommitEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (where != null) {
                if (where.getApp() != null && StringUtils.isNotEmpty(where.getApp().getId())) {
                    Join<Object, Object> join = root.join(CommitEntity_.APP, JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get(AppEntity_.ID), where.getApp().getId()));
                }
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<CommitEntity> result = null;
        if (skip == null) skip = 0;
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.commitRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.commitRepository.findAll(specification, sort);
        }
        return result.stream().map(this.commitMapper::toDto).collect(Collectors.toList());
    }
}
