package io.xmeta.admin.service;

import io.jsonwebtoken.lang.Assert;
import io.xmeta.admin.constants.FieldConstProperties;
import io.xmeta.admin.domain.*;
import io.xmeta.admin.mapper.EntityFieldMapper;
import io.xmeta.admin.model.*;
import io.xmeta.admin.repository.EntityFieldRepository;
import io.xmeta.admin.repository.EntityRepository;
import io.xmeta.admin.repository.EntityVersionRepository;
import io.xmeta.admin.util.Inflector;
import io.xmeta.core.utils.Maps;
import io.xmeta.admin.util.PredicateBuilder;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.RelationType;
import io.xmeta.core.domain.RelationTypeConstants;
import io.xmeta.core.service.DataTypeMapping;
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
    private final DataTypeMapping dataTypeMapping;

    public EntityFieldService(EntityFieldRepository entityFieldRepository, EntityVersionRepository entityVersionRepository, EntityFieldMapper entityFieldMapper, EntityRepository entityRepository, LockService lockService, DataTypeMapping dataTypeMapping) {
        super(entityFieldRepository);
        this.entityFieldRepository = entityFieldRepository;
        this.entityVersionRepository = entityVersionRepository;
        this.entityFieldMapper = entityFieldMapper;
        this.entityRepository = entityRepository;
        this.lockService = lockService;
        this.dataTypeMapping = dataTypeMapping;
    }

    public List<EntityField> fields(Entity entity, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityFieldMapper.toDto(this.getVersionFields(entity.getId(), 0, where, orderBy, skip, take));
    }

    public List<EntityField> fields(EntityVersion entityVersion, EntityFieldWhereInput where, EntityFieldOrderByInput orderBy, Integer skip, Integer take) {
        return this.entityFieldMapper.toDto(this.getVersionFields(entityVersion.getEntityId(),
                entityVersion.getVersionNumber(), where, orderBy, skip, take));
    }

    /**
     * ??????????????????
     *
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
        if (entityField.getProperties() != null && entityField.getProperties().size() > 0) {
            fieldEntity.setProperties(ObjectMapperUtils.toBytes(entityField.getProperties()));
        } else {
            fieldEntity.setProperties(ObjectMapperUtils.toBytes(getDefaultFieldProperties(entityField.getDataType())));
        }

        fieldEntity.setRequired(entityField.getRequired());
        fieldEntity.setSearchable(entityField.getSearchable());
        fieldEntity.setDescription(entityField.getDescription());
        fieldEntity.setPosition(entityField.getPosition());
        fieldEntity.setUnique(entityField.getUnique());
        fieldEntity.setColumn(entityField.getColumn());
        if (entityField.getDataType() != null) {
            EnumDataType enumDataType = entityField.getDataType();
            DataType dataType = DataType.valueOf(enumDataType.name());
            if (EntityFieldUtils.isControlledJavaType(dataType)) {
                fieldEntity.setJavaType(dataTypeMapping.javaTypeFor(dataType).getName());
            }
        }
        if (entityField.getInputType() != null) {
            fieldEntity.setInputType(entityField.getInputType().name());
        }

        if (log.isDebugEnabled()) {
            log.debug("Create default field with entity: {}, version: {}, displayName: {}, name: {}", entityVersion.getName(), entityVersion.getId(), entityField.getDisplayName(), entityField.getName());
        }

        this.entityFieldRepository.save(fieldEntity);

        return this.entityFieldMapper.toDto(fieldEntity);
    }

    /**
     * ????????????
     *
     * @param data
     * @param relatedFieldName
     * @param relatedFieldDisplayName
     * @return
     */
    @Transactional
    public EntityField createEntityField(EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName) {

        EntityEntity entityEntity = this.lockService.acquireEntityLock(data.getEntity().getConnect().getId());

        //this.validateFieldMutationArgs();

        //this.validateFieldData();
        String fieldId = IDGenerator.nextId(); //??????id

        //TODO
        if (data.getDataType() == EnumDataType.Lookup) {
            RelationType relationType = EntityFieldUtils.getRelationType(data.getProperties());
            if (relationType == null) {
                throw new RuntimeException("relation type is null");
            }
            boolean needCreateRelation = relationType != RelationType.OneWay;
            if (needCreateRelation) {
                //??????relatedFieldId
                data.getProperties().put(RelationTypeConstants.RELATED_FIELD_ID, IDGenerator.nextId());

                RelationType targetRelationType = EntityFieldUtils.getTargetRelationType(relationType);
                boolean dominant = MapUtils.getBooleanValue(data.getProperties(), RelationTypeConstants.RELATION_DOMINANT, false);
                this.createRelatedField(
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_FIELD_ID),
                        relatedFieldName,
                        relatedFieldDisplayName,
                        targetRelationType,
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID),
                        entityEntity.getId(),
                        fieldId,
                        !dominant
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

        EnumDataType enumDataType = data.getDataType();
        DataType dataType = DataType.valueOf(enumDataType.name());
        if (EntityFieldUtils.isControlledJavaType(dataType)) {
            entityFieldEntity.setJavaType(dataTypeMapping.javaTypeFor(dataType).getName());
        }

        entityFieldEntity.setDisplayName(data.getDisplayName());
        entityFieldEntity.setDataType(data.getDataType().name());
        entityFieldEntity.setProperties(ObjectMapperUtils.toBytes(data.getProperties()));
        entityFieldEntity.setRequired(data.getRequired());
        entityFieldEntity.setSearchable(data.getSearchable());
        entityFieldEntity.setDescription(data.getDescription());
        entityFieldEntity.setPosition(0);
        entityFieldEntity.setUnique(data.getUnique());

        if (log.isDebugEnabled()) {
            log.debug("Create field with entity: {},version: {}, displayName: {}, name: {}", entityVersion.getName(),entityVersion.getId(), entityFieldEntity.getDisplayName(), entityFieldEntity.getName());
        }

        this.entityFieldRepository.saveAndFlush(entityFieldEntity);

        return this.entityFieldMapper.toDto(entityFieldEntity);
    }

    /***
     * ????????????displayName??????????????????
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

        RelationType relationType = EntityFieldUtils.getRelationType(createInput.getProperties());
        if (createInput.getDataType() == EnumDataType.Lookup && relationType!=null && relationType != RelationType.OneWay) {

            relatedFieldName = Inflector.getInstance().lowerCamelCase(entityEntity.getName()
                    , ' ');

            relatedFieldDisplayName = entityEntity.getDisplayName();
        }
        return this.createEntityField(createInput, relatedFieldName, relatedFieldDisplayName);
    }

    /**
     * ???????????????????????????EntityField ?????? name??? dataType, properties
     *
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

        if (dataType == null) {
            List<EntityEntity> entities = this.entityRepository.findEntitiesByName(name, entity.getApp().getId());
            if (entities != null && entities.size() > 0) {
                EntityEntity relatedEntity = entities.get(0);
                // The created field would be multiple selection if its name is equal to
                // the related entity's plural display name

                // The related field allow multiple selection should be the opposite of
                // the field's

                // The related field name should resemble the name of the field's entity
                String relatedFieldName = Inflector.getInstance().lowerCamelCase(entity.getName(), ' ');
                if (isFieldNameAvailable(relatedFieldName, relatedEntity.getId())) {
                    //TODO relation ????????????????????????
                    Map<String, Object> properties = data.getProperties();
                    String rtType =  MapUtils.getString(properties, RelationTypeConstants.RELATION_TYPE, RelationType.ManyToOne.name());
                    RelationType relationType = RelationType.valueOf(rtType);

                    builder.setDataType(EnumDataType.Lookup)
                            .setProperties(
                                    Maps.of(RelationTypeConstants.RELATED_ENTITY_ID, relatedEntity.getId())
                                            .and(RelationTypeConstants.RELATION_TYPE, relationType.name())
                                            .build()
                            )
                            .setColumn(name);
                    return builder.build();
                }

            }
        }

        if (dataType == null) {
            dataType = EnumDataType.SingleLineText; // ??????dataType ?????????
        }

        builder.setDataType(dataType);

        if (data.getProperties()!=null && data.getProperties().size()>0) {
            builder.setProperties(data.getProperties());
        } else {
            builder.setProperties(getDefaultFieldProperties(dataType));
        }

        return builder.build();
    }

    //??????
    private Map<String, Object> getDefaultFieldProperties(EnumDataType dataType) {
        Assert.notNull(dataType, "DataType can't be null");
        DataType type = DataType.fromName(dataType.name());
        Assert.notNull(type, dataType.name() + "??????????????????????????????");
        return type.getProperties();
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
        if (orderBy == null) {
            orderBy = EntityFieldOrderByInput.builder().setCreatedAt(SortOrder.Asc).build();
        }
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
        //?????????????????????????????????????????????????????????????????????????????????
        // isSystemDataType
        // Delete related field in case field data type is changed from lookup
        boolean shouldDeleteRelated = (StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name()) &&
                data.getDataType() != EnumDataType.Lookup) ||
                (StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name()) && data.getDataType() == EnumDataType.Lookup &&
                        !StringUtils.equals(MapUtils.getString(properties, RelationTypeConstants.RELATION_TYPE), RelationType.OneWay.name()) &&
                        StringUtils.equals(MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATION_TYPE), RelationType.OneWay.name()));

        // Create related field in case field data type is changed to lookup
        boolean shouldCreateRelated = data.getDataType() == EnumDataType.Lookup &&
                !StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name());

        boolean shouldChangeRelated = !shouldCreateRelated &&
                !shouldDeleteRelated && (!StringUtils.equals(MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID),
                MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID)));

        //??????????????????????????????
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
            boolean needCreateRelation = relationType != RelationType.OneWay;
            if (needCreateRelation) {
                RelationType targetRelationType = EntityFieldUtils.getTargetRelationType(relationType);
                this.createRelatedField(
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_FIELD_ID),
                        relatedFieldName,
                        relatedFieldDisplayName,
                        targetRelationType,
                        MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATED_ENTITY_ID),
                        entity.getId(),
                        field.getPermanentId(),
                        relationType == RelationType.ManyToMany
                );
            }
        }
        if (shouldUpdateRelated) {
            RelationType relationType = EntityFieldUtils.getRelationType(data.getProperties());
            if (relationType == null) {
                throw new RuntimeException("relation type is null");
            }
            boolean needUpdateRelation = relationType != RelationType.OneWay;
            if (needUpdateRelation) {
                RelationType targetRelationType = EntityFieldUtils.getTargetRelationType(relationType);
                this.updateRelatedField(MapUtils.getString(properties, RelationTypeConstants.RELATED_ENTITY_ID),
                        MapUtils.getString(properties, RelationTypeConstants.RELATED_FIELD_ID),
                        targetRelationType);
            }
        }

        //update dominant
        if (data.getDataType() == EnumDataType.Lookup &&
                StringUtils.equals(MapUtils.getString(data.getProperties(), RelationTypeConstants.RELATION_TYPE), RelationType.ManyToMany.name())) {
            data.getProperties().put(RelationTypeConstants.RELATION_DOMINANT, true);
        }

        field.setUpdatedAt(ZonedDateTime.now());
        field.setName(data.getName());
        field.setDisplayName(data.getDisplayName());
        field.setDataType(data.getDataType().name());
        field.setJavaType(dataTypeMapping.javaTypeFor(data.getDataType().name()).getName());
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
     * ??????????????????
     *
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
                                   String entityId, String relatedEntityId, String relatedFieldId, boolean dominant) {
        this.lockService.acquireEntityLock(entityId);

        Optional<EntityVersionEntity> entityVersionEntity = this.entityVersionRepository.getCurrentVersion(entityId);
        if (!entityVersionEntity.isPresent()) {
            throw new RuntimeException("can't find entity version");
        }
        EntityVersionEntity versionEntity = entityVersionEntity.get();
        Maps.MapBuilder<String, Object> propertyBuilder = Maps.of(RelationTypeConstants.RELATION_TYPE, relationType.name())
                .and(RelationTypeConstants.RELATED_ENTITY_ID, relatedEntityId)
                .and(RelationTypeConstants.RELATED_FIELD_ID, relatedFieldId);
        if (relationType == RelationType.ManyToMany) {
            propertyBuilder.and(RelationTypeConstants.RELATION_DOMINANT, dominant);
        }
        EntityFieldEntity entityFieldEntity = new EntityFieldEntity();
        entityFieldEntity.setCreatedAt(ZonedDateTime.now());
        entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
        entityFieldEntity.setEntityVersion(versionEntity);
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

        if (log.isDebugEnabled()) {
            log.debug("Create related field with entity: {}, version: {}, displayName: {}, name: {}", versionEntity.getName(), versionEntity.getId(), entityFieldEntity.getDisplayName(), entityFieldEntity.getName());
        }

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
        if (relationType == RelationType.ManyToMany) {
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
