package io.xmeta.admin.service;

import io.xmeta.admin.constants.FieldConstProperties;
import io.xmeta.admin.domain.*;
import io.xmeta.admin.mapper.EntityFieldMapper;
import io.xmeta.admin.model.*;
import io.xmeta.admin.repository.EntityFieldRepository;
import io.xmeta.admin.repository.EntityRepository;
import io.xmeta.admin.repository.EntityVersionRepository;
import io.xmeta.admin.util.*;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.RelationType;
import io.xmeta.core.domain.RelationTypeConstants;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.IDGenerator;
import io.xmeta.core.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @Description
 * @Author Jeff
 * @Date 2021-09-05
 */

@Service
@Transactional(readOnly = true)
@Slf4j
@PreAuthorize("isAuthenticated()")
public class EntityFieldService extends BaseService<EntityFieldRepository, EntityFieldEntity, String> {

    private final EntityFieldRepository entityFieldRepository;
    private final EntityVersionRepository entityVersionRepository;
    private final EntityFieldMapper entityFieldMapper;
    private final EntityRepository entityRepository;
    private final LockService lockService;

    public EntityFieldService(EntityFieldRepository entityFieldRepository, EntityVersionRepository entityVersionRepository, EntityFieldMapper entityFieldMapper, EntityRepository entityRepository, LockService lockService) {
        super(entityFieldRepository);
        this.entityFieldRepository = entityFieldRepository;
        this.entityVersionRepository = entityVersionRepository;
        this.entityFieldMapper = entityFieldMapper;
        this.entityRepository = entityRepository;
        this.lockService = lockService;
    }

    public List<EntityField> fields(Entity entity, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityFieldMapper.toDto(this.getVersionFields(entity.getId(), 0, where, orderBy, skip, take));
    }

    public List<EntityField> fields(EntityVersion entityVersion, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityFieldMapper.toDto(this.getVersionFields(entityVersion.getEntityId(),
                entityVersion.getVersionNumber(), where, orderBy, skip, take));
    }

    /**
     *  创建字段信息
     * @param entityField
     * @param entityVersion
     * @return
     */
    @Transactional
    public EntityField createDefaultField(EntityField entityField, EntityVersionEntity entityVersion) {
        EntityFieldEntity fieldEntity = new EntityFieldEntity();
        fieldEntity.setCreatedAt(ZonedDateTime.now());
        fieldEntity.setUpdatedAt(ZonedDateTime.now());
        fieldEntity.setEntityVersion(entityVersion);
        fieldEntity.setPermanentId(IDGenerator.nextId());
        fieldEntity.setName(entityField.getName());
        fieldEntity.setDisplayName(entityField.getDisplayName());
        fieldEntity.setDataType(entityField.getDataType().name());
        fieldEntity.setProperties(ObjectMapperUtils.toBytes(entityField.getProperties()));
        fieldEntity.setRequired(entityField.getRequired());
        fieldEntity.setSearchable(entityField.getSearchable());
        fieldEntity.setDescription(entityField.getDescription());
        fieldEntity.setPosition(entityField.getPosition());
        fieldEntity.setUnique(entityField.getUnique());
        fieldEntity.setColumn(entityField.getColumn());
        if (entityField.getDataType()!= null) {
            fieldEntity.setJavaType(EntityFieldUtils.determineJavaType(entityField.getDataType().name(), entityField.getProperties()));
        }
        if (entityField.getInputType() != null) {
            fieldEntity.setInputType(entityField.getInputType().name());
        }

        this.entityFieldRepository.save(fieldEntity);

        return this.entityFieldMapper.toDto(fieldEntity);
    }

    /**
     * 创建字段
     * @param data
     * @param relatedFieldName
     * @param relatedFieldDisplayName
     * @return
     */
    @Transactional
    public EntityField createEntityField(EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName) {

        EntityEntity entityEntity = this.lockService.acquireEntityLock(data.getEntity().getConnect().getId());

        //this.validateFieldMutationArgs();
        if (data.getDataType() == EnumDataType.Lookup) {
            //生成relatedFieldId
            data.getProperties().put(RelationTypeConstants.RELATED_FIELD_ID, IDGenerator.nextId());
        }
        //this.validateFieldData();
        String fieldId = IDGenerator.nextId(); //永久id

        //TODO
        if (data.getDataType() == EnumDataType.Lookup) {
            RelationType relationType = EntityFieldUtils.getRelationType(data.getProperties());
            if (relationType == null) {
                throw new RuntimeException("relation type is null");
            }
            boolean needCreateRelation =  relationType != RelationType.oneWay;
            if (needCreateRelation) {
                RelationType targetRelationType = EntityFieldUtils.getTargetRelationType(relationType);
                this.createRelatedField(
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_FIELD_ID),
                        relatedFieldName,
                        relatedFieldDisplayName,
                        targetRelationType,
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID),
                        entityEntity.getId(),
                        fieldId
                );
            }
        }

        EntityVersionEntity entityVersion = this.entityVersionRepository.findEntityVersion(entityEntity.getId(), 0);
        EntityFieldEntity entityFieldEntity = new EntityFieldEntity();
        entityFieldEntity.setCreatedAt(ZonedDateTime.now());
        entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
        entityFieldEntity.setEntityVersion(entityVersion);
        entityFieldEntity.setPermanentId(fieldId);
        entityFieldEntity.setName(data.getName());
        entityFieldEntity.setColumn(data.getColumn());
        if (data.getInputType() != null) {
            entityFieldEntity.setInputType(data.getInputType().name());
        }
        entityFieldEntity.setJavaType(EntityFieldUtils.determineJavaType(data.getDataType().name(), data.getProperties()));
        entityFieldEntity.setDisplayName(data.getDisplayName());
        entityFieldEntity.setDataType(data.getDataType().name());
        entityFieldEntity.setProperties(ObjectMapperUtils.toBytes(data.getProperties()));
        entityFieldEntity.setRequired(data.getRequired());
        entityFieldEntity.setSearchable(data.getSearchable());
        entityFieldEntity.setDescription(data.getDescription());
        entityFieldEntity.setPosition(0);
        entityFieldEntity.setUnique(data.getUnique());

        this.entityFieldRepository.saveAndFlush(entityFieldEntity);

        return this.entityFieldMapper.toDto(entityFieldEntity);
    }

    /***
     * 前台输入displayName快捷创建字段
     * @param data
     * @return
     */
    @Transactional
    public EntityField createEntityFieldByDisplayName(EntityFieldCreateByDisplayNameInput data) {
        // validate the entity

        EntityEntity entityEntity = this.entityRepository.getById(data.getEntity().getConnect().getId());

        EntityFieldCreateInput createInput = this.createFieldCreateInputByDisplayName(data);

        createInput.setDisplayName(data.getDisplayName());
        createInput.setEntity(data.getEntity());

        String relatedFieldName = null;
        String relatedFieldDisplayName = null;

        if (createInput.getDataType() == EnumDataType.Lookup) {

            RelationType relationType = EntityFieldUtils.getRelationType(createInput.getProperties());

            boolean allowMultipleSelection = EntityFieldUtils.allowMultipleSelection(relationType);

            relatedFieldName = Inflector.getInstance().lowerCamelCase(
                    !allowMultipleSelection ? entityEntity.getPluralDisplayName() : entityEntity.getName()
                    , ' ');

            relatedFieldDisplayName = !allowMultipleSelection
                    ? entityEntity.getPluralDisplayName()
                    : entityEntity.getDisplayName();
        }
        return this.createEntityField(createInput, relatedFieldName, relatedFieldDisplayName);
    }

    /**
     * 创建一个供存储用的EntityField 设置 name， dataType, properties
     * @param data
     * @return
     */
    public EntityFieldCreateInput createFieldCreateInputByDisplayName(EntityFieldCreateByDisplayNameInput data) {

        EntityFieldCreateInput.Builder builder = EntityFieldCreateInput.builder();

        String entityId = data.getEntity().getConnect().getId();

        EntityEntity entity = this.entityRepository.getById(entityId);

        String displayName = data.getDisplayName();
        String lowerCaseName = displayName.toLowerCase();
        String name = Inflector.getInstance().lowerCamelCase(displayName, ' ');
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
                dataType = EnumDataType.SingleLineText;
            }
        }

        builder.setName(name);
        builder.setEntity(WhereParentIdInput.builder().setConnect(WhereUniqueInput.builder().setId(entityId).build()).build());
        builder.setColumn(name);

        if (dataType == EnumDataType.Lookup || dataType == null) {
            List<EntityEntity> entities = this.entityRepository.findEntitiesByName(name, entity.getApp().getId());
            if (entities != null && entities.size() > 0) {
                EntityEntity relatedEntity = entities.get(0);
                // The created field would be multiple selection if its name is equal to
                // the related entity's plural display name
                boolean allowMultipleSelection = StringUtils.equals(relatedEntity.getPluralDisplayName(), lowerCaseName);

                // The related field allow multiple selection should be the opposite of
                // the field's
                boolean relatedFieldAllowMultipleSelection = !allowMultipleSelection;

                // The related field name should resemble the name of the field's entity
                String relatedFieldName = Inflector.getInstance().lowerCamelCase(
                        relatedFieldAllowMultipleSelection
                                ? entity.getName()
                                : entity.getPluralDisplayName(), ' ');
                if (isFieldNameAvailable(relatedFieldName, relatedEntity.getId())) {
                    //TODO relation 类型需要重新处理
                    builder.setDataType(EnumDataType.Lookup)
                            .setProperties(
                                    Maps.of(RelationTypeConstants.RELATED_ENTITY_ID, relatedEntity.getId())
                                            .and(RelationTypeConstants.RELATION_TYPE, RelationType.manyToOne.name())
                                            .build()
                            )
                            .setColumn(name);
                    return builder.build();
                }

            }
        }

        if (dataType == null)  {
            dataType = EnumDataType.SingleLineText; // 保存dataType 不为空
        }

        builder.setDataType(dataType)
                .setProperties(getDefaultFieldProperties(dataType));

        return builder.build();
    }

    //获取
    private Map<String, Object> getDefaultFieldProperties(EnumDataType dataType) {
        if (dataType == null) {
            return FieldConstProperties.SingleLineText;
        }
        switch (dataType) {
            case SingleLineText:
                return FieldConstProperties.SingleLineText;
            case MultiLineText:
                return FieldConstProperties.MultiLineText;
            case WholeNumber:
                return FieldConstProperties.WholeNumber;
            case DecimalNumber:
                return FieldConstProperties.DecimalNumber;
            case DateTime:
                return FieldConstProperties.DateTime;
            case Lookup:
                return FieldConstProperties.Lookup;
            case OptionSet:
                return FieldConstProperties.OptionSet;
            case MultiSelectOptionSet:
                return FieldConstProperties.MultiSelectOptionSet;
            default:
                return new HashMap<>();
        }
    }

    private boolean isFieldNameAvailable(String name, String entityId) {
        EntityField entityField = EntityField.builder().setName(name).build();
        EntityFieldWhereInput.Builder builder = EntityFieldWhereInput.builder();
        StringFilter stringFilter = new StringFilter();
        stringFilter.setEq(name);
        builder.setName(stringFilter);

        return CollectionUtils.isEmpty(getVersionFields(entityId, 0, builder.build(), null, null, null));
    }

    public List<EntityFieldEntity> getVersionFields(String entityId, Integer versionNumber,
                                                    EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        Specification<EntityFieldEntity> specification = Specification.where(null);
        Specification<EntityFieldEntity> condition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = null;

            if (where != null) {
                if (where.getId() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(EntityFieldEntity_.ID),
                            where.getId()));
                }
                if (where.getPermanentId() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(EntityFieldEntity_.PERMANENT_ID),
                            where.getPermanentId()));
                }
                if (where.getName() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(EntityFieldEntity_.NAME),
                            where.getName()));
                }
                if (where.getDisplayName() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(EntityFieldEntity_.DISPLAY_NAME),
                            where.getDisplayName()));
                }

                if (where.getDescription() != null) {
                    predicates.addAll(PredicateBuilder.buildStringFilter(criteriaBuilder,
                            root.get(EntityFieldEntity_.DESCRIPTION),
                            where.getDescription()));
                }

                if (where.getDataType() != null) {
                    predicates.addAll(PredicateBuilder.buildEnumDataTypeFilter(criteriaBuilder,
                            root.get(EntityFieldEntity_.DESCRIPTION),
                            where.getDataType()));
                }

            }

            if (StringUtils.isNotEmpty(entityId) || versionNumber != null) {
                Join<Object, Object> join = root.join(EntityFieldEntity_.ENTITY_VERSION, JoinType.LEFT);

                if (StringUtils.isNotEmpty(entityId)) {
                    predicate = criteriaBuilder.equal(join.get(EntityVersionEntity_.ENTITY_ID), entityId);
                    predicates.add(predicate);
                }
                if (versionNumber != null) {
                    predicate = criteriaBuilder.equal(join.get(EntityVersionEntity_.VERSION_NUMBER), versionNumber);
                    predicates.add(predicate);
                }
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
        specification = specification.and(condition);
        Sort sort = createSort(orderBy);
        List<EntityFieldEntity> result = null;
        if (skip == null) {
            skip = 0;
        }
        if (take != null) {
            Pageable pageable = PageRequest.of(skip, take, sort);
            result = this.entityFieldRepository.findAll(specification, pageable).getContent();
        } else {
            result = this.entityFieldRepository.findAll(specification, sort);
        }

        return result;
    }

    @Transactional
    public EntityField deleteEntityField(String id) {
        EntityFieldEntity entityFieldEntity = this.entityFieldRepository.getById(id);
        this.lockService.acquireEntityLock(entityFieldEntity.getEntityVersion().getEntityId());
        if (EnumDataType.Lookup.name().equals(entityFieldEntity.getDataType())) {
            Map<String, Object> properties = ObjectMapperUtils.toMap(entityFieldEntity.getProperties());
            String relatedFieldId = MapUtils.getString(properties, RelationTypeConstants.RELATED_FIELD_ID);
            String relatedEntityId = MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID);
            this.deleteRelatedField(relatedFieldId, relatedEntityId);
        }
        this.entityFieldRepository.deleteById(entityFieldEntity.getId());
        return this.entityFieldMapper.toDto(entityFieldEntity);
    }

    @Transactional
    public EntityField updateEntityField(EntityFieldUpdateInput data, WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName) {
        EntityFieldEntity field = this.entityFieldRepository.getById(where.getId());

        Map<String, Object> properties = ObjectMapperUtils.toMap(field.getProperties());
        //将提交数据与数据库中数据对比，判断是否需要更新关联字段
        // isSystemDataType
        // Delete related field in case field data type is changed from lookup
        boolean shouldDeleteRelated = (StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name()) &&
                data.getDataType() != EnumDataType.Lookup) ||
                (StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name()) && data.getDataType() == EnumDataType.Lookup &&
                        !StringUtils.equals(MapUtils.getString(properties, RelationTypeConstants.RELATION_TYPE), RelationType.oneWay.name()) &&
                        StringUtils.equals(MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATION_TYPE), RelationType.oneWay.name()));

        // Create related field in case field data type is changed to lookup
        boolean shouldCreateRelated = data.getDataType() == EnumDataType.Lookup &&
                !StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name());

        boolean shouldChangeRelated = !shouldCreateRelated &&
                !shouldDeleteRelated && (!StringUtils.equals(MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID),
                MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID)));

        //是否需要更新关联属性
        boolean shouldUpdateRelated = !shouldCreateRelated &&
                !shouldDeleteRelated && StringUtils.equals(MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID),
                MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID)) && !StringUtils.equals(
                        MapUtils.getString(properties, RelationTypeConstants.RELATION_TYPE),
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATION_TYPE)
                );

        log.info("shouldDeleteRelated: {}, shouldCreateRelated: {}, shouldChangeRelated: {}, shouldUpdateRelated: {}",
                shouldDeleteRelated, shouldCreateRelated, shouldChangeRelated, shouldUpdateRelated);

        EntityEntity entity = this.lockService.acquireEntityLock(field.getEntityVersion().getEntityId());

        //this.validateFieldMutationArgs
        if (shouldCreateRelated || shouldChangeRelated) {
            data.getProperties().put(RelationTypeConstants.RELATED_FIELD_ID, IDGenerator.nextId());
        }

        //this.validateFieldData
        if (shouldDeleteRelated || shouldChangeRelated) {
            this.deleteRelatedField(MapUtils.getString(properties, RelationTypeConstants.RELATED_FIELD_ID),
                    MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID));
        }

        if (shouldCreateRelated || shouldChangeRelated) {

            RelationType relationType = EntityFieldUtils.getRelationType(data.getProperties());
            if (relationType == null) {
                throw new RuntimeException("relation type is null");
            }
            boolean needCreateRelation =  relationType != RelationType.oneWay;
            if (needCreateRelation) {
                RelationType targetRelationType = EntityFieldUtils.getTargetRelationType(relationType);
                this.createRelatedField(
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_FIELD_ID),
                        relatedFieldName,
                        relatedFieldDisplayName,
                        targetRelationType,
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID),
                        entity.getId(),
                        field.getPermanentId()
                );
            }
        }
        if (shouldUpdateRelated) {
            RelationType relationType = EntityFieldUtils.getRelationType(data.getProperties());
            if (relationType == null) {
                throw new RuntimeException("relation type is null");
            }
            boolean needUpdateRelation =  relationType != RelationType.oneWay;
            if (needUpdateRelation) {
                RelationType targetRelationType = EntityFieldUtils.getTargetRelationType(relationType);
                this.updateRelatedField(MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID),
                        MapUtils.getString(properties, RelationTypeConstants.RELATED_FIELD_ID),
                        targetRelationType);
            }
        }

        //update dominant
        if (data.getDataType() == EnumDataType.Lookup &&
                StringUtils.equals(MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATION_TYPE), RelationType.manyToMany.name())) {
            data.getProperties().put(RelationTypeConstants.RELATION_DOMINANT, true);
        }

        field.setUpdatedAt(ZonedDateTime.now());
        field.setName(data.getName());
        field.setDisplayName(data.getDisplayName());
        field.setDataType(data.getDataType().name());
        field.setJavaType(EntityFieldUtils.determineJavaType(data.getDataType().name(), data.getProperties()));
        field.setProperties(ObjectMapperUtils.toBytes(data.getProperties()));
        field.setRequired(data.getRequired());
        field.setSearchable(data.getSearchable());
        field.setDescription(data.getDescription());
        field.setPosition(data.getPosition());
        field.setUnique(data.getUnique());
        field.setColumn(data.getColumn());
        this.entityFieldRepository.save(field);
        return this.entityFieldMapper.toDto(field);
    }


    /**
     * 创建关联字段
     * @param permanentId
     * @param name
     * @param displayName
     * @param relationType
     * @param entityId
     * @param relatedEntityId
     * @param relatedFieldId
     */
    @Transactional
    public void createRelatedField(String permanentId, String name, String displayName, RelationType relationType,
                                   String entityId, String relatedEntityId, String relatedFieldId) {
        this.lockService.acquireEntityLock(entityId);

        Optional<EntityVersionEntity> entityVersionEntity = this.entityVersionRepository.getCurrentVersion(entityId);
        if (!entityVersionEntity.isPresent()) {
            throw new RuntimeException("can't find entity version");
        }
        Maps.MapBuilder<String, Object> propertyBuilder = Maps.of(RelationTypeConstants.RELATION_TYPE, relationType.name())
                .and(RelationTypeConstants.RELATED_ENTITY_ID, relatedEntityId)
                .and(RelationTypeConstants.RELATED_FIELD_ID, relatedFieldId);
        if (relationType == RelationType.manyToMany) {
            propertyBuilder.and(RelationTypeConstants.RELATION_DOMINANT, false);
        }
        EntityFieldEntity entityFieldEntity = new EntityFieldEntity();
        entityFieldEntity.setCreatedAt(ZonedDateTime.now());
        entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
        entityFieldEntity.setEntityVersion(entityVersionEntity.get());
        entityFieldEntity.setPermanentId(permanentId);
        entityFieldEntity.setName(name);
        entityFieldEntity.setDisplayName(displayName);
        entityFieldEntity.setColumn(name);
        entityFieldEntity.setDataType(EnumDataType.Lookup.name());
        entityFieldEntity.setProperties(ObjectMapperUtils.toBytes(propertyBuilder.build()));
        entityFieldEntity.setRequired(false);
        entityFieldEntity.setSearchable(true);
        entityFieldEntity.setDescription("");
        entityFieldEntity.setPosition(0);
        entityFieldEntity.setUnique(false);
        this.entityFieldRepository.save(entityFieldEntity);
    }

    public void updateRelatedField(String relatedEntityId, String relatedFieldId, RelationType relationType) {

        Optional<EntityVersionEntity> entityVersionEntity = this.entityVersionRepository.getCurrentVersion(relatedEntityId);
        if (!entityVersionEntity.isPresent()) {
            throw new RuntimeException("can't find entity version");
        }

        this.lockService.acquireEntityLock(relatedEntityId);

        EntityFieldEntity entityFieldEntity = getRelatedField(relatedFieldId, relatedEntityId);
        entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
        entityFieldEntity.setEntityVersion(entityVersionEntity.get());
        Map<String, Object> properties = ObjectMapperUtils.toMap(entityFieldEntity.getProperties());
        properties.put(RelationTypeConstants.RELATION_TYPE, relationType.name());
        if (relationType == RelationType.manyToMany) {
            properties.put(RelationTypeConstants.RELATION_DOMINANT, false);
        } else {
            properties.remove(RelationTypeConstants.RELATION_DOMINANT);
        }
        entityFieldEntity.setProperties(ObjectMapperUtils.toBytes(properties));
        this.entityFieldRepository.save(entityFieldEntity);
    }

    public EntityFieldEntity getRelatedField(String relatedFieldId, String relatedEntityId) {
        EntityFieldWhereInput.Builder builder = EntityFieldWhereInput.builder();
        builder.setPermanentId(StringFilter.builder().setEq(relatedFieldId).build());
        List<EntityFieldEntity> versionFields = this.getVersionFields(relatedEntityId, 0, builder.build(), null, null, null);
        if (versionFields.size() == 0) {
            throw new RuntimeException("can't find relation fields");
        }
        if (versionFields.size() > 1) {
            throw new RuntimeException("more than one relation fields founded");
        }
        return versionFields.get(0);
    }

    @Transactional
    public void deleteRelatedField(String relatedFieldId, String relatedEntityId) {
        this.lockService.acquireEntityLock(relatedEntityId);
        EntityFieldEntity entityField = getRelatedField(relatedFieldId, relatedEntityId);
        int count = this.entityFieldRepository.deleteByEntityVersionAndPermanentId(entityField.getEntityVersion().getId(), relatedFieldId);
        if (count == 0) {
            log.error("can't remove entity field: {}", entityField.getName());
            throw new RuntimeException("can't remove entity field: " + entityField.getName());
        }
    }

    public List<io.xmeta.core.domain.EntityField> getFields(String versionId) {
        List<EntityFieldEntity> fieldEntities = this.entityFieldRepository.getFields(versionId);
        List<io.xmeta.core.domain.EntityField> fieldDomains = new ArrayList<>();
        for (EntityFieldEntity field : fieldEntities) {
            io.xmeta.core.domain.EntityField entityField = new io.xmeta.core.domain.EntityField();
            entityField.setId(field.getPermanentId());
            entityField.setName(field.getName());
            entityField.setColumn(field.getColumn());
            entityField.setDisplayName(field.getDisplayName());
            entityField.setDataType(DataType.valueOf(field.getDataType()));
            entityField.setJavaType(field.getJavaType());
            entityField.setProperties(ObjectMapperUtils.toMap(field.getProperties()));
            entityField.setRequired(field.getRequired());
            entityField.setSearchable(field.getSearchable());
            entityField.setDescription(field.getDescription());
            entityField.setUnique(field.getUnique());
            fieldDomains.add(entityField);
        }
        return fieldDomains;
    }

}
