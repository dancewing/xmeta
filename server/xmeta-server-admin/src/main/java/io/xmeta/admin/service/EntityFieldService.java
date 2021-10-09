package io.xmeta.admin.service;

import io.xmeta.admin.constants.FieldConstProperties;
import io.xmeta.admin.domain.EntityEntity;
import io.xmeta.admin.domain.EntityFieldEntity;
import io.xmeta.admin.domain.EntityVersionEntity;
import io.xmeta.admin.mix.CreateOneEntityField;
import io.xmeta.admin.mix.FieldDomain;
import io.xmeta.admin.repository.EntityFieldRepository;
import io.xmeta.admin.repository.EntityRepository;
import io.xmeta.admin.repository.EntityVersionRepository;
import io.xmeta.admin.util.*;
import io.xmeta.graphql.domain.*;
import io.xmeta.admin.mapper.EntityFieldMapper;
import io.xmeta.graphql.model.*;
import io.xmeta.graphql.util.*;
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


    @Transactional
    public EntityField createDefaultField(EntityField entityField, String entityId, EntityVersionEntity entityVersion) {
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
        fieldEntity.setJavaType(entityField.getJavaType());
        if (entityField.getInputType()!=null) {
            fieldEntity.setInputType(entityField.getInputType().name());
        }

        this.entityFieldRepository.save(fieldEntity);

        return this.entityFieldMapper.toDto(fieldEntity);
    }

    @Transactional
    public EntityField createEntityField(EntityFieldCreateInput data, String relatedFieldName, String relatedFieldDisplayName) {
        CreateOneEntityField createOneEntityField = new CreateOneEntityField();
        createOneEntityField.setData(data);
        createOneEntityField.setRelatedFieldName(relatedFieldName);
        createOneEntityField.setRelatedFieldDisplayName(relatedFieldDisplayName);
        return this.createField(createOneEntityField);
    }

    @Transactional
    public EntityField createEntityFieldByDisplayName(EntityFieldCreateByDisplayNameInput data) {
        // validate the entity

        EntityEntity entityEntity = this.entityRepository.getById(data.getEntity().getConnect().getId());

        EntityFieldCreateInput createInput = this.createFieldCreateInputByDisplayName(data);

        createInput.setDisplayName(data.getDisplayName());
        createInput.setEntity(data.getEntity());

        CreateOneEntityField createOneEntityField = new CreateOneEntityField();
        createOneEntityField.setData(createInput);

        if (createInput.getDataType() == EnumDataType.Lookup) {
            //TODO allowMultipleSelection, 从properties 属性中获取
            boolean allowMultipleSelection =
                    createInput.getProperties() != null && MapUtils.getBooleanValue(createInput.getProperties(), "allowMultipleSelection",
                            false);
            createOneEntityField.setRelatedFieldName(Inflector.getInstance().lowerCamelCase(
                    !allowMultipleSelection ? entityEntity.getPluralDisplayName() : entityEntity.getName()
                    , ' '));

            createOneEntityField.setRelatedFieldDisplayName(!allowMultipleSelection
                    ? entityEntity.getPluralDisplayName()
                    : entityEntity.getDisplayName());
        }
        return this.createField(createOneEntityField);
    }

    @Transactional
    public EntityField createField(CreateOneEntityField createOneEntityField) {
        EntityFieldCreateInput fieldData = createOneEntityField.getData();

        EntityEntity entityEntity = this.lockService.acquireEntityLock(fieldData.getEntity().getConnect().getId());

        //this.validateFieldMutationArgs();


        if (fieldData.getDataType() == EnumDataType.Lookup) {
            //生成relatedFieldId
            fieldData.getProperties().put("relatedFieldId", IDGenerator.nextId());
        }
        //this.validateFieldData();
        String fieldId = IDGenerator.nextId();

        if (fieldData.getDataType() == EnumDataType.Lookup) {
            this.createRelatedField(
                    MapUtils.getString(fieldData.getProperties(), "relatedFieldId"),
                    createOneEntityField.getRelatedFieldName(),
                    createOneEntityField.getRelatedFieldDisplayName(),
                    !MapUtils.getBooleanValue(fieldData.getProperties(), "allowMultipleSelection"),
                    MapUtils.getString(fieldData.getProperties(), "relatedEntityId"),
                    entityEntity.getId(),
                    fieldId
            );
        }

        EntityVersionEntity entityVersion = this.entityVersionRepository.findEntityVersion(entityEntity.getId(), 0);
        EntityFieldEntity entityFieldEntity = new EntityFieldEntity();
        entityFieldEntity.setId(fieldId);
        entityFieldEntity.setCreatedAt(ZonedDateTime.now());
        entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
        entityFieldEntity.setEntityVersion(entityVersion);
        entityFieldEntity.setPermanentId(fieldId);
        entityFieldEntity.setName(fieldData.getName());
        entityFieldEntity.setColumn(fieldData.getColumn());
        if (fieldData.getInputType()!=null) {
            entityFieldEntity.setInputType(fieldData.getInputType().name());
        }
        entityFieldEntity.setJavaType(fieldData.getJavaType());
        entityFieldEntity.setDisplayName(fieldData.getDisplayName());
        entityFieldEntity.setDataType(fieldData.getDataType().name());
        entityFieldEntity.setProperties(ObjectMapperUtils.toBytes(fieldData.getProperties()));
        entityFieldEntity.setRequired(fieldData.getRequired());
        entityFieldEntity.setSearchable(fieldData.getSearchable());
        entityFieldEntity.setDescription(fieldData.getDescription());
        entityFieldEntity.setPosition(0);
        entityFieldEntity.setUnique(fieldData.getUnique());

        this.entityFieldRepository.saveAndFlush(entityFieldEntity);

        return this.entityFieldMapper.toDto(entityFieldEntity);
    }

    // 创建一个供存储用的EntityField 设置 name， dataType, properties
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
                dataType = EnumDataType.WholeNumber;
            }
        }

        if (dataType == EnumDataType.Lookup || dataType == null) {
            List<EntityEntity> entities = this.entityRepository.findEntityByNames(name, entity.getApp().getId());
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
                    builder.setName(name)
                            .setDataType(EnumDataType.Lookup)
                            .setProperties(
                                    Maps.of("relatedEntityId", relatedEntity.getId())
                                            .and("allowMultipleSelection", true)
                                            .build());
                    return builder.build();
                }

            }
        }

        builder.setName(name).setDataType(dataType == null ? EnumDataType.SingleLineText : dataType)
                .setProperties(getDefaultFieldProperties(dataType));
        builder.setColumn(name);
        builder.setEntity(WhereParentIdInput.builder().setConnect(WhereUniqueInput.builder().setId(entityId).build()).build());

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
        if (skip == null) skip = 0;
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
            String relatedFieldId = MapUtils.getString(properties, "relatedFieldId");
            String relatedEntityId = MapUtils.getString(properties, "relatedEntityId");
            this.deleteRelatedField(relatedFieldId, relatedEntityId);
        }
        this.entityFieldRepository.deleteById(entityFieldEntity.getId());
        return this.entityFieldMapper.toDto(entityFieldEntity);
    }

    @Transactional
    public EntityField updateEntityField(EntityFieldUpdateInput data, WhereUniqueInput where, String relatedFieldName, String relatedFieldDisplayName) {
        EntityFieldEntity field = this.entityFieldRepository.getById(where.getId());

        Map<String, Object> properties = ObjectMapperUtils.toMap(field.getProperties());
        // isSystemDataType
        // Delete related field in case field data type is changed from lookup
        boolean shouldDeleteRelated = StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name()) &&
                data.getDataType() != EnumDataType.Lookup;

        // Create related field in case field data type is changed to lookup
        boolean shouldCreateRelated = data.getDataType() == EnumDataType.Lookup &&
                !StringUtils.equals(field.getDataType(), EnumDataType.Lookup.name());

        boolean shouldChangeRelated = !shouldCreateRelated &&
                !shouldDeleteRelated && !StringUtils.equals(MapUtils.getString(properties, "relatedEntityId"),
                MapUtils.getString(data.getProperties(), "relatedEntityId"));

        EntityEntity entity = this.lockService.acquireEntityLock(field.getEntityVersion().getEntityId());

        //this.validateFieldMutationArgs

        if (shouldCreateRelated || shouldChangeRelated) {
            data.getProperties().put("relatedFieldId", IDGenerator.nextId());
        }

        //this.validateFieldData
        if (shouldDeleteRelated || shouldChangeRelated) {
            this.deleteRelatedField(MapUtils.getString(data.getProperties(), "relatedFieldId"),
                    MapUtils.getString(data.getProperties(), "relatedEntityId"));
        }

        if (shouldCreateRelated || shouldChangeRelated) {

            this.createRelatedField(
                    MapUtils.getString(data.getProperties(), "relatedFieldId"),
                    relatedFieldName,
                    relatedFieldDisplayName,
                    !MapUtils.getBooleanValue(data.getProperties(), "allowMultipleSelection"),
                    MapUtils.getString(data.getProperties(), "relatedEntityId"),
                    entity.getId(),
                    field.getPermanentId()
            );
        }

        field.setUpdatedAt(ZonedDateTime.now());
        // field.setPermanentId("");
        field.setName(data.getName());
        field.setDisplayName(data.getDisplayName());
        field.setDataType(data.getDataType().name());
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


    @Transactional
    public void createRelatedField(String id, String name, String displayName, boolean allowMultipleSelection,
                                   String entityId, String relatedEntityId, String relatedFieldId) {
        this.lockService.acquireEntityLock(entityId);

        Optional<EntityVersionEntity> entityVersionEntity = this.entityVersionRepository.getCurrentVersion(entityId);
        if (!entityVersionEntity.isPresent()) {
            throw new RuntimeException("can't find entity version");
        }
        EntityFieldEntity entityFieldEntity = new EntityFieldEntity();
        entityFieldEntity.setCreatedAt(ZonedDateTime.now());
        entityFieldEntity.setUpdatedAt(ZonedDateTime.now());
        entityFieldEntity.setEntityVersion(entityVersionEntity.get());
        entityFieldEntity.setPermanentId(id);
        entityFieldEntity.setName(name);
        entityFieldEntity.setDisplayName(displayName);
        entityFieldEntity.setDataType(EnumDataType.Lookup.name());
        entityFieldEntity.setProperties(ObjectMapperUtils.toBytes(Maps.of("allowMultipleSelection", allowMultipleSelection)
                .and("relatedEntityId", relatedEntityId)
                .and("relatedFieldId", relatedFieldId).build()));
        entityFieldEntity.setRequired(false);
        entityFieldEntity.setSearchable(true);
        entityFieldEntity.setDescription("");
        entityFieldEntity.setPosition(0);
        entityFieldEntity.setUnique(false);
        this.entityFieldRepository.save(entityFieldEntity);
    }

    @Transactional
    public void deleteRelatedField(String relatedFieldId, String relatedEntityId) {
        this.lockService.acquireEntityLock(relatedEntityId);
        EntityFieldWhereInput.Builder builder = EntityFieldWhereInput.builder();
        builder.setPermanentId(StringFilter.builder().setEq(relatedFieldId).build());
        List<EntityFieldEntity> versionFields = this.getVersionFields(relatedEntityId, 0, builder.build(), null, null, null);
        if (versionFields.size() == 0) {
            throw new RuntimeException("can't find relation fields");
        }
        if (versionFields.size() > 1) {
            throw new RuntimeException("more than one relation fields founded");
        }
        EntityFieldEntity entityField = versionFields.get(0);
        this.entityFieldRepository.deleteByEntityVersionAndPermanentId(entityField.getEntityVersion().getId(), relatedEntityId);
    }

    public List<FieldDomain> getFields(String versionId) {
        List<EntityFieldEntity> fieldEntities = this.entityFieldRepository.getFields(versionId);
        List<FieldDomain> fieldDomains = new ArrayList<>();
        for (EntityFieldEntity fieldEntity : fieldEntities) {
            FieldDomain fieldDomain = new FieldDomain();
            fieldDomain.setId(fieldEntity.getId());
            fieldDomain.setPermanentId(fieldEntity.getPermanentId());
            fieldDomain.setName(fieldEntity.getName());
            fieldDomain.setDisplayName(fieldEntity.getDisplayName());
            fieldDomain.setDataType(fieldEntity.getDataType());
            fieldDomain.setProperties(ObjectMapperUtils.toMap(fieldEntity.getProperties()));
            fieldDomain.setRequired(fieldEntity.getRequired());
            fieldDomain.setSearchable(fieldEntity.getSearchable());
            fieldDomain.setDescription(fieldEntity.getDescription());
            fieldDomain.setPosition(fieldEntity.getPosition());
            fieldDomain.setUnique(fieldEntity.getUnique());
            fieldDomains.add(fieldDomain);
        }
        return fieldDomains;
    }

}
