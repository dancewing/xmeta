package io.xmeta.graphql.service;

import io.xmeta.graphql.domain.*;
import io.xmeta.graphql.mapper.EntityFieldMapper;
import io.xmeta.graphql.model.EntityField;
import io.xmeta.graphql.model.EntityFieldCreateByDisplayNameInput;
import io.xmeta.graphql.model.EntityFieldCreateInput;
import io.xmeta.graphql.model.EnumDataType;
import io.xmeta.graphql.repository.EntityFieldRepository;
import io.xmeta.graphql.repository.EntityRepository;
import io.xmeta.graphql.repository.EntityVersionRepository;
import io.xmeta.graphql.util.Inflector;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author  Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional
public class EntityFieldService extends BaseService<EntityFieldRepository, EntityFieldEntity, String> {

    private final EntityFieldRepository entityFieldRepository;
    private final EntityVersionRepository entityVersionRepository;
    private final EntityFieldMapper entityFieldMapper;
    private final EntityRepository entityRepository;

    public EntityFieldService(EntityFieldRepository entityFieldRepository, EntityVersionRepository entityVersionRepository, EntityFieldMapper entityFieldMapper, EntityRepository entityRepository) {
        super(entityFieldRepository);
        this.entityFieldRepository = entityFieldRepository;
        this.entityVersionRepository = entityVersionRepository;
        this.entityFieldMapper = entityFieldMapper;
        this.entityRepository = entityRepository;
    }

    @Transactional
    public EntityField create(EntityField entityField, String entityId, EntityVersionEntity entityVersion) {
        EntityFieldEntity fieldEntity = new EntityFieldEntity();
        fieldEntity.setCreatedAt(ZonedDateTime.now());
        fieldEntity.setUpdatedAt(ZonedDateTime.now());
        fieldEntity.setEntityVersion(entityVersion);
        fieldEntity.setPermanentId(entityField.getPermanentId());
        fieldEntity.setName(entityField.getName());
        fieldEntity.setDisplayName(entityField.getDisplayName());
        fieldEntity.setDataType(entityField.getDataType().name());
        fieldEntity.setProperties(entityField.getProperties());
        fieldEntity.setRequired(entityField.getRequired());
        fieldEntity.setSearchable(entityField.getSearchable());
        fieldEntity.setDescription(entityField.getDescription());
        fieldEntity.setPosition(entityField.getPosition());
        fieldEntity.setUnique(entityField.getUnique());

        this.entityFieldRepository.save(fieldEntity);

        EntityEntity entityEntity = new EntityEntity();
        entityEntity.setId(entityId);

        EntityVersionEntity entityVersionEntity = new EntityVersionEntity();
        entityVersionEntity.setCreatedAt(ZonedDateTime.now());
        entityVersionEntity.setUpdatedAt(ZonedDateTime.now());
        entityVersionEntity.setEntity(entityEntity);
        entityVersionEntity.setVersionNumber(0);
        entityVersionEntity.setName(entityField.getName());
        entityVersionEntity.setDisplayName(entityField.getDisplayName());
       // TODO
       // entityVersionEntity.setPluralDisplayName(entityField.get);
        entityVersionEntity.setDescription(entityField.getDescription());
        entityVersionEntity.setDeleted(null);
        this.entityVersionRepository.save(entityVersionEntity);

        return this.entityFieldMapper.toDto(fieldEntity);
    }

    public EntityField createEntityField(EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName) {
        return null;
    }

    // 创建一个供存储用的EntityField
    public EntityField createEntityFieldByDisplayName(EntityFieldCreateByDisplayNameInput data) {

        EntityField.Builder builder = EntityField.builder();

        String entityId = data.getEntity().getConnect().getId();

        EntityEntity entity = this.entityRepository.getById(entityId);

        String displayName = data.getDisplayName();
        String lowerCaseName = displayName.toLowerCase();
        String name = Inflector.getInstance().camelCase(displayName, false);
        EnumDataType dataType = data.getDataType();
        if (dataType == null) {
            // guest dataType
            if (lowerCaseName.contains("date")) {
                dataType = EnumDataType.DateTime;
            } else if (lowerCaseName.contains("description") || lowerCaseName.contains("comment")) {
                dataType = EnumDataType.MultiLineText;
            } else if (lowerCaseName.contains("email")) {
                dataType = EnumDataType.Email;
            } else if (lowerCaseName.contains("status")) {
                dataType = EnumDataType.OptionSet;
            } else if (lowerCaseName.startsWith("is")) {
                dataType = EnumDataType.Boolean;
            } else if (lowerCaseName.contains("price")) {
                dataType = EnumDataType.DecimalNumber;
            } else if (
                    lowerCaseName.contains("quantity") ||
                            lowerCaseName.contains("qty")
            ) {
                dataType = EnumDataType.WholeNumber;
            }
        }

        if (dataType == EnumDataType.Lookup || dataType == null) {
            List<EntityEntity> entities = this.entityRepository.findEntityByNames(name, entity.getApp().getId());
            if (entities!=null && entities.size()>0) {
                EntityEntity relatedEntity = entities.get(0);
                // The created field would be multiple selection if its name is equal to
                // the related entity's plural display name
                boolean allowMultipleSelection = StringUtils.equals(relatedEntity.getPluralDisplayName(), lowerCaseName);

                // The related field allow multiple selection should be the opposite of
                // the field's
                boolean relatedFieldAllowMultipleSelection = !allowMultipleSelection;

                // The related field name should resemble the name of the field's entity
                String relatedFieldName = Inflector.getInstance().camelCase(
                        relatedFieldAllowMultipleSelection
                                ? entity.getName()
                                : entity.getPluralDisplayName(), false);
                if (isFieldNameAvailable(relatedFieldName, relatedEntity.getId())) {
                    //TODO relation 类型需要重新处理
                    builder.setName(name)
                            .setDataType(EnumDataType.Lookup)
                            .setProperties("");
                    return builder.build();
                }

            }
        }

        builder.setName(name).setDataType(dataType==null ? EnumDataType.SingleLineText: dataType)
                .setProperties("");

        return builder.build();
    }

    private boolean isFieldNameAvailable(String name, String entityId) {
        EntityField entityField = EntityField.builder().setName(name).build();
        return CollectionUtils.isEmpty(getFields(entityId, entityField));
    }

    private List<EntityFieldEntity> getFields(String entityId, EntityField entityField) {
        return getVersionFields(entityId, 0L, entityField);
    }


    public List<EntityFieldEntity> getVersionFields(String entityId, Long versionNumber, EntityField entityField) {
        Specification<EntityFieldEntity> specification = Specification.where(null);
        Specification<EntityFieldEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = null;

            if (StringUtils.isNotEmpty(entityField.getName())) {
                predicate = criteriaBuilder.equal(root.get(EntityFieldEntity_.NAME), entityField.getName());
                predicates.add(predicate);
            }

            if (StringUtils.isNotEmpty(entityId) && versionNumber!=null) {
                Join<Object, Object> join = root.join(EntityFieldEntity_.ENTITY_VERSION, JoinType.LEFT);
                predicate = criteriaBuilder.equal(join.get(EntityVersionEntity_.ENTITY_ID), entityId);
                predicates.add(predicate);

                predicate = criteriaBuilder.equal(join.get(EntityVersionEntity_.VERSION_NUMBER), versionNumber);
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        return this.entityFieldRepository.findAll(specification);
    }

}
