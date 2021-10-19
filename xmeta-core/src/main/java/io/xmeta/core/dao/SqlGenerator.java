package io.xmeta.core.dao;

import io.xmeta.core.MetaException;
import io.xmeta.core.domain.DataType;
import io.xmeta.core.domain.Entity;
import io.xmeta.core.domain.EntityField;
import io.xmeta.core.utils.EntityFieldUtils;
import io.xmeta.core.utils.EntityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.convert.Identifier;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.mapping.PersistentPropertyPath;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.RenderContextFactory;
import org.springframework.data.relational.core.mapping.PersistentPropertyPathExtension;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.render.RenderContext;
import org.springframework.data.relational.core.sql.render.SqlRenderer;
import org.springframework.data.util.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SqlGenerator {

    static final SqlIdentifier VERSION_SQL_PARAMETER = SqlIdentifier.unquoted("___oldOptimisticLockingVersion");
    static final SqlIdentifier ID_SQL_PARAMETER = SqlIdentifier.unquoted("id");
    static final SqlIdentifier IDS_SQL_PARAMETER = SqlIdentifier.unquoted("ids");
    static final SqlIdentifier ROOT_ID_PARAMETER = SqlIdentifier.unquoted("rootId");

    private static final Pattern parameterPattern = Pattern.compile("\\W");
    private final RenderContext renderContext;

    private final SqlContext sqlContext;
    private final SqlRenderer sqlRenderer;
    private final SqlGenerator.Columns columns;

    private final Lazy<String> findOneSql = Lazy.of(this::createFindOneSql);
    private final Lazy<String> findAllSql = Lazy.of(this::createFindAllSql);
    private final Lazy<String> findAllInListSql = Lazy.of(this::createFindAllInListSql);

    private final Lazy<String> existsSql = Lazy.of(this::createExistsSql);
    private final Lazy<String> countSql = Lazy.of(this::createCountSql);

    private final Lazy<String> updateSql = Lazy.of(this::createUpdateSql);
    private final Lazy<String> updateWithVersionSql = Lazy.of(this::createUpdateWithVersionSql);

    private final Lazy<String> deleteByIdSql = Lazy.of(this::createDeleteSql);
    private final Lazy<String> deleteByIdAndVersionSql = Lazy.of(this::createDeleteByIdAndVersionSql);
    private final Lazy<String> deleteByListSql = Lazy.of(this::createDeleteByListSql);

    /**
     * Create a new {@link SqlGenerator} given {@link RelationalMappingContext} and {@link RelationalPersistentEntity}.
     *
     * @param converter must not be {@literal null}.
     * @param entity must not be {@literal null}.
     * @param dialect must not be {@literal null}.
     */
    SqlGenerator(JdbcConverter converter, Entity entity,  Dialect dialect) {
        this.sqlContext = new SqlContext(entity);
        this.sqlRenderer = SqlRenderer.create(new RenderContextFactory(dialect).createRenderContext());
        this.columns = new SqlGenerator.Columns(entity, converter);
        this.renderContext = new RenderContextFactory(dialect).createRenderContext();
    }

    /**
     * Construct a IN-condition based on a {@link Select Sub-Select} which selects the ids (or stand ins for ids) of the
     * given {@literal path} to those that reference the root entities specified by the {@literal rootCondition}.
     *
     * @param path specifies the table and id to select
     * @param rootCondition the condition on the root of the path determining what to select
     * @param filterColumn the column to apply the IN-condition to.
     * @return the IN condition
     */
    private Condition getSubselectCondition(PersistentPropertyPathExtension path,
                                            Function<Column, Condition> rootCondition, Column filterColumn) {

        PersistentPropertyPathExtension parentPath = path.getParentPath();

        if (!parentPath.hasIdProperty()) {
            if (parentPath.getLength() > 1) {
                return getSubselectCondition(parentPath, rootCondition, filterColumn);
            }
            return rootCondition.apply(filterColumn);
        }

        Table subSelectTable = Table.create(parentPath.getTableName());
        Column idColumn = subSelectTable.column(parentPath.getIdColumnName());
        Column selectFilterColumn = subSelectTable.column(parentPath.getEffectiveIdColumnName());

        Condition innerCondition;

        if (parentPath.getLength() == 1) { // if the parent is the root of the path

            // apply the rootCondition
            innerCondition = rootCondition.apply(selectFilterColumn);
        } else {

            // otherwise we need another layer of subselect
            innerCondition = getSubselectCondition(parentPath, rootCondition, selectFilterColumn);
        }

        Select select = Select.builder() //
                .select(idColumn) //
                .from(subSelectTable) //
                .where(innerCondition).build();

        return filterColumn.in(select);
    }

    private BindMarker getBindMarker(SqlIdentifier columnName) {
        return SQL.bindMarker(":" + parameterPattern.matcher(renderReference(columnName)).replaceAll(""));
    }

    /**
     * Returns a query for selecting all simple properties of an entity, including those for one-to-one relationships.
     * Results are filtered using an {@code IN}-clause on the id column.
     *
     * @return a SQL statement. Guaranteed to be not {@code null}.
     */
    String getFindAllInList() {
        return findAllInListSql.get();
    }

    /**
     * Returns a query for selecting all simple properties of an entity, including those for one-to-one relationships.
     *
     * @return a SQL statement. Guaranteed to be not {@code null}.
     */
    String getFindAll() {
        return findAllSql.get();
    }

    /**
     * Returns a query for selecting all simple properties of an entity, including those for one-to-one relationships,
     * sorted by the given parameter.
     *
     * @return a SQL statement. Guaranteed to be not {@code null}.
     */
    String getFindAll(Sort sort) {
        return render(selectBuilder(Collections.emptyList(), sort, Pageable.unpaged()).build());
    }

    /**
     * Returns a query for selecting all simple properties of an entity, including those for one-to-one relationships,
     * paged and sorted by the given parameter.
     *
     * @return a SQL statement. Guaranteed to be not {@code null}.
     */
    String getFindAll(Pageable pageable) {
        return render(selectBuilder(Collections.emptyList(), pageable.getSort(), pageable).build());
    }

    /**
     * Returns a query for selecting all simple properties of an entity, including those for one-to-one relationships.
     * Results are limited to those rows referencing some other entity using the column specified by
     * {@literal columnName}. This is used to select values for a complex property ({@link Set}, {@link Map} ...) based on
     * a referencing entity.
     *
     * @param parentIdentifier name of the column of the FK back to the referencing entity.
     * @param keyColumn if the property is of type {@link Map} this column contains the map key.
     * @param ordered whether the SQL statement should include an ORDER BY for the keyColumn. If this is {@code true}, the
     *          keyColumn must not be {@code null}.
     * @return a SQL String.
     */
    String getFindAllByProperty(Identifier parentIdentifier, @Nullable SqlIdentifier keyColumn, boolean ordered) {

        Assert.isTrue(keyColumn != null || !ordered,
                "If the SQL statement should be ordered a keyColumn to order by must be provided.");

        Table table = getTable();

        SelectBuilder.SelectWhere builder = selectBuilder( //
                keyColumn == null //
                        ? Collections.emptyList() //
                        : Collections.singleton(keyColumn) //
        );

        Condition condition = buildConditionForBackReference(parentIdentifier, table);
        SelectBuilder.SelectWhereAndOr withWhereClause = builder.where(condition);

        Select select = ordered //
                ? withWhereClause.orderBy(table.column(keyColumn).as(keyColumn)).build() //
                : withWhereClause.build();

        return render(select);
    }

    private Condition buildConditionForBackReference(Identifier parentIdentifier, Table table) {

        Condition condition = null;
        for (SqlIdentifier backReferenceColumn : parentIdentifier.toMap().keySet()) {

            Condition newCondition = table.column(backReferenceColumn).isEqualTo(getBindMarker(backReferenceColumn));
            condition = condition == null ? newCondition : condition.and(newCondition);
        }

        Assert.state(condition != null, "We need at least one condition");

        return condition;
    }

    /**
     * Create a {@code SELECT COUNT(id) FROM … WHERE :id = …} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getExists() {
        return existsSql.get();
    }

    /**
     * Create a {@code SELECT … FROM … WHERE :id = …} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getFindOne() {
        return findOneSql.get();
    }

    /**
     * Create a {@code SELECT count(id) FROM … WHERE :id = … (LOCK CLAUSE)} statement.
     *
     * @param lockMode Lock clause mode.
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getAcquireLockById(LockMode lockMode) {
        return this.createAcquireLockById(lockMode);
    }

    /**
     * Create a {@code SELECT count(id) FROM … (LOCK CLAUSE)} statement.
     *
     * @param lockMode Lock clause mode.
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getAcquireLockAll(LockMode lockMode) {
        return this.createAcquireLockAll(lockMode);
    }

    /**
     * Create a {@code INSERT INTO … (…) VALUES(…)} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getInsert(Set<SqlIdentifier> additionalColumns) {
        return createInsertSql(additionalColumns);
    }

    /**
     * Create a {@code UPDATE … SET …} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getUpdate() {
        return updateSql.get();
    }

    /**
     * Create a {@code UPDATE … SET … WHERE ID = :id and VERSION_COLUMN = :___oldOptimisticLockingVersion } statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getUpdateWithVersion() {
        return updateWithVersionSql.get();
    }

    /**
     * Create a {@code SELECT COUNT(*) FROM …} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getCount() {
        return countSql.get();
    }

    /**
     * Create a {@code DELETE FROM … WHERE :id = …} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getDeleteById() {
        return deleteByIdSql.get();
    }

    /**
     * Create a {@code DELETE FROM … WHERE :id = … and :___oldOptimisticLockingVersion = ...} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getDeleteByIdAndVersion() {
        return deleteByIdAndVersionSql.get();
    }

    /**
     * Create a {@code DELETE FROM … WHERE :ids in (…)} statement.
     *
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String getDeleteByList() {
        return deleteByListSql.get();
    }

    /**
     * Create a {@code DELETE} query and optionally filter by {@link PersistentPropertyPath}.
     *
     * @param path can be {@literal null}.
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    String createDeleteAllSql(@Nullable PersistentPropertyPath<RelationalPersistentProperty> path) {

        Table table = getTable();

        DeleteBuilder.DeleteWhere deleteAll = Delete.builder().from(table);

        if (path == null) {
            return render(deleteAll.build());
        }

       // return createDeleteByPathAndCriteria(new PersistentPropertyPathExtension(mappingContext, path), Column::isNotNull);
        return null;
    }

    /**
     * Create a {@code DELETE} query and filter by {@link PersistentPropertyPath}.
     *
     * @param path must not be {@literal null}.
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
/*    String createDeleteByPath(PersistentPropertyPath<RelationalPersistentProperty> path) {
        return createDeleteByPathAndCriteria(new PersistentPropertyPathExtension(mappingContext, path),
                filterColumn -> filterColumn.isEqualTo(getBindMarker(ROOT_ID_PARAMETER)));
    }*/

    private String createFindOneSql() {

        Select select = selectBuilder().where(getIdColumn().isEqualTo(getBindMarker(ID_SQL_PARAMETER))) //
                .build();

        return render(select);
    }

    private String createAcquireLockById(LockMode lockMode) {

        Table table = this.getTable();

        Select select = StatementBuilder //
                .select(getIdColumn()) //
                .from(table) //
                .where(getIdColumn().isEqualTo(getBindMarker(ID_SQL_PARAMETER))) //
                .lock(lockMode) //
                .build();

        return render(select);
    }

    private String createAcquireLockAll(LockMode lockMode) {

        Table table = this.getTable();

        Select select = StatementBuilder //
                .select(getIdColumn()) //
                .from(table) //
                .lock(lockMode) //
                .build();

        return render(select);
    }

    private String createFindAllSql() {
        return render(selectBuilder().build());
    }

    private SelectBuilder.SelectWhere selectBuilder() {
        return selectBuilder(Collections.emptyList());
    }

    private SelectBuilder.SelectWhere selectBuilder(Collection<SqlIdentifier> keyColumns) {

        Table table = getTable();
        Entity entity = getEntity();

        List<Expression> columnExpressions = new ArrayList<>();

        List<SqlGenerator.Join> joinTables = new ArrayList<>();

        EntityUtils.doWithAll(entity, field -> {

            if (EntityFieldUtils.isSelectable(field)) {
                Column column = sqlContext.getColumn(field);
                if (column != null) {
                    columnExpressions.add(column);
                }
            }
            //TODO add relation
        });

        for (SqlIdentifier keyColumn : keyColumns) {
            columnExpressions.add(table.column(keyColumn).as(keyColumn));
        }

        SelectBuilder.SelectAndFrom selectBuilder = StatementBuilder.select(columnExpressions);
        SelectBuilder.SelectJoin baseSelect = selectBuilder.from(table);

        for (SqlGenerator.Join join : joinTables) {
            baseSelect = baseSelect.leftOuterJoin(join.joinTable).on(join.joinColumn).equals(join.parentId);
        }

        return (SelectBuilder.SelectWhere) baseSelect;
    }

    private SelectBuilder.SelectOrdered selectBuilder(Collection<SqlIdentifier> keyColumns, Sort sort,
                                                      Pageable pageable) {

        SelectBuilder.SelectOrdered sortable = this.selectBuilder(keyColumns);
        sortable = applyPagination(pageable, sortable);
        return sortable.orderBy(extractOrderByFields(sort));

    }

    private SelectBuilder.SelectOrdered applyPagination(Pageable pageable, SelectBuilder.SelectOrdered select) {

        if (!pageable.isPaged()) {
            return select;
        }

        Assert.isTrue(select instanceof SelectBuilder.SelectLimitOffset,
                () -> String.format("Can't apply limit clause to statement of type %s", select.getClass()));

        SelectBuilder.SelectLimitOffset limitable = (SelectBuilder.SelectLimitOffset) select;
        SelectBuilder.SelectLimitOffset limitResult = limitable.limitOffset(pageable.getPageSize(), pageable.getOffset());

        Assert.state(limitResult instanceof SelectBuilder.SelectOrdered, String.format(
                "The result of applying the limit-clause must be of type SelectOrdered in order to apply the order-by-clause but is of type %s.",
                select.getClass()));

        return (SelectBuilder.SelectOrdered) limitResult;
    }

    /**
     * Create a {@link Column} for {@link PersistentPropertyPathExtension}.
     *
     * @param path the path to the column in question.
     * @return the statement as a {@link String}. Guaranteed to be not {@literal null}.
     */
    @Nullable
    Column getColumn(PersistentPropertyPathExtension path) {

        // an embedded itself doesn't give an column, its members will though.
        // if there is a collection or map on the path it won't get selected at all, but it will get loaded with a separate
        // select
        // only the parent path is considered in order to handle arrays that get stored as BINARY properly
        if (path.isEmbedded() || path.getParentPath().isMultiValued()) {
            return null;
        }

        if (path.isEntity()) {

            // Simple entities without id include there backreference as an synthetic id in order to distinguish null entities
            // from entities with only null values.

            if (path.isQualified() //
                    || path.isCollectionLike() //
                    || path.hasIdProperty() //
            ) {
                return null;
            }

            return sqlContext.getReverseColumn(path);
        }

        return sqlContext.getColumn(path);
    }

    @Nullable
    SqlGenerator.Join getJoin(PersistentPropertyPathExtension path) {

        if (!path.isEntity() || path.isEmbedded() || path.isMultiValued()) {
            return null;
        }

        Table currentTable = sqlContext.getTable(path);

        PersistentPropertyPathExtension idDefiningParentPath = path.getIdDefiningParentPath();
        Table parentTable = sqlContext.getTable(idDefiningParentPath);

        return new SqlGenerator.Join( //
                currentTable, //
                currentTable.column(path.getReverseColumnName()), //
                parentTable.column(idDefiningParentPath.getIdColumnName()) //
        );
    }

    private String createFindAllInListSql() {

        Select select = selectBuilder().where(getIdColumn().in(getBindMarker(IDS_SQL_PARAMETER))).build();

        return render(select);
    }

    private String createExistsSql() {

        Table table = getTable();

        Select select = StatementBuilder //
                .select(Functions.count(getIdColumn())) //
                .from(table) //
                .where(getIdColumn().isEqualTo(getBindMarker(ID_SQL_PARAMETER))) //
                .build();

        return render(select);
    }

    private String createCountSql() {

        Table table = getTable();

        Select select = StatementBuilder //
                .select(Functions.count(Expressions.asterisk())) //
                .from(table) //
                .build();

        return render(select);
    }

    private String createInsertSql(Set<SqlIdentifier> additionalColumns) {

        Table table = getTable();

        Set<SqlIdentifier> columnNamesForInsert = new TreeSet<>(Comparator.comparing(SqlIdentifier::getReference));
       // columnNamesForInsert.addAll(columns.getInsertableColumns());
        columnNamesForInsert.addAll(additionalColumns);

        InsertBuilder.InsertIntoColumnsAndValuesWithBuild insert = Insert.builder().into(table);

        for (SqlIdentifier cn : columnNamesForInsert) {
            insert = insert.column(table.column(cn));
        }

        InsertBuilder.InsertValuesWithBuild insertWithValues = null;
        for (SqlIdentifier cn : columnNamesForInsert) {
            insertWithValues = (insertWithValues == null ? insert : insertWithValues).values(getBindMarker(cn));
        }

        return render(insertWithValues == null ? insert.build() : insertWithValues.build());
    }

    private String createUpdateSql() {
        return render(createBaseUpdate().build());
    }

    private String createUpdateWithVersionSql() {

        Update update = createBaseUpdate() //
                .and(getVersionColumn().isEqualTo(SQL.bindMarker(":" + renderReference(VERSION_SQL_PARAMETER)))) //
                .build();

        return render(update);
    }

    private UpdateBuilder.UpdateWhereAndOr createBaseUpdate() {

        Table table = getTable();

        List<AssignValue> assignments = columns.getUpdateableColumns() //
                .stream() //
                .map(columnName -> Assignments.value( //
                        table.column(columnName), //
                        getBindMarker(columnName))) //
                .collect(Collectors.toList());

        EntityField id = EntityFieldUtils.findPK(getEntity()).orElseThrow(MetaException::new);

        SqlIdentifier sqlIdentifier = SqlIdentifier.unquoted(id.getColumn());

        return Update.builder() //
                .table(table) //
                .set(assignments) //
                .where(getIdColumn().isEqualTo(getBindMarker(sqlIdentifier)));
    }

    private String createDeleteSql() {
        return render(createBaseDeleteById(getTable()).build());
    }

    private String createDeleteByIdAndVersionSql() {

        Delete delete = createBaseDeleteById(getTable()) //
                .and(getVersionColumn().isEqualTo(SQL.bindMarker(":" + renderReference(VERSION_SQL_PARAMETER)))) //
                .build();

        return render(delete);
    }

    private DeleteBuilder.DeleteWhereAndOr createBaseDeleteById(Table table) {
        return Delete.builder().from(table)
                .where(getIdColumn().isEqualTo(SQL.bindMarker(":" + renderReference(ID_SQL_PARAMETER))));
    }

    private String createDeleteByPathAndCriteria(PersistentPropertyPathExtension path,
                                                 Function<Column, Condition> rootCondition) {

        Table table = Table.create(path.getTableName());

        DeleteBuilder.DeleteWhere builder = Delete.builder() //
                .from(table);
        Delete delete;

        Column filterColumn = table.column(path.getReverseColumnName());

        if (path.getLength() == 1) {

            delete = builder //
                    .where(rootCondition.apply(filterColumn)) //
                    .build();
        } else {

            Condition condition = getSubselectCondition(path, rootCondition, filterColumn);
            delete = builder.where(condition).build();
        }

        return render(delete);
    }

    private String createDeleteByListSql() {

        Table table = getTable();

        Delete delete = Delete.builder() //
                .from(table) //
                .where(getIdColumn().in(getBindMarker(IDS_SQL_PARAMETER))) //
                .build();

        return render(delete);
    }

    private String render(Select select) {
        return this.sqlRenderer.render(select);
    }

    private String render(Insert insert) {
        return this.sqlRenderer.render(insert);
    }

    private String render(Update update) {
        return this.sqlRenderer.render(update);
    }

    private String render(Delete delete) {
        return this.sqlRenderer.render(delete);
    }

    private Table getTable() {
        return sqlContext.getTable();
    }

    private Entity getEntity() {
        return sqlContext.getEntity();
    }

    private Column getIdColumn() {
        return sqlContext.getIdColumn();
    }

    private Column getVersionColumn() {
        return sqlContext.getVersionColumn();
    }

    private String renderReference(SqlIdentifier identifier) {
        return identifier.getReference(renderContext.getIdentifierProcessing());
    }

    private List<OrderByField> extractOrderByFields(Sort sort) {

        return sort.stream() //
                .map(this::orderToOrderByField) //
                .collect(Collectors.toList());
    }

    private OrderByField orderToOrderByField(Sort.Order order) {

/*        SqlIdentifier columnName = this.entity.getRequiredPersistentProperty(order.getProperty()).getColumnName();
        Column column = Column.create(columnName, this.getTable());
        return OrderByField.from(column, order.getDirection());*/
        return null;
    }

    /**
     * Value object representing a {@code JOIN} association.
     */
    static final class Join {

        private final Table joinTable;
        private final Column joinColumn;
        private final Column parentId;

        Join(Table joinTable, Column joinColumn, Column parentId) {

            Assert.notNull( joinTable,"JoinTable must not be null.");
            Assert.notNull( joinColumn,"JoinColumn must not be null.");
            Assert.notNull( parentId,"ParentId must not be null.");

            this.joinTable = joinTable;
            this.joinColumn = joinColumn;
            this.parentId = parentId;
        }

        Table getJoinTable() {
            return this.joinTable;
        }

        Column getJoinColumn() {
            return this.joinColumn;
        }

        Column getParentId() {
            return this.parentId;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SqlGenerator.Join join = (SqlGenerator.Join) o;
            return joinTable.equals(join.joinTable) &&
                    joinColumn.equals(join.joinColumn) &&
                    parentId.equals(join.parentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(joinTable, joinColumn, parentId);
        }

        @Override
        public String toString() {

            return "Join{" +
                    "joinTable=" + joinTable +
                    ", joinColumn=" + joinColumn +
                    ", parentId=" + parentId +
                    '}';
        }
    }

    /**
     * Value object encapsulating column name caches.
     *
     * @author Mark Paluch
     * @author Jens Schauder
     */
    static class Columns {

        private final JdbcConverter converter;

        private final List<SqlIdentifier> columnNames = new ArrayList<>();
        private final List<SqlIdentifier> idColumnNames = new ArrayList<>();
        private final List<SqlIdentifier> nonIdColumnNames = new ArrayList<>();
        private final Set<SqlIdentifier> readOnlyColumnNames = new HashSet<>();
        private final Set<SqlIdentifier> insertableColumns;
        private final Set<SqlIdentifier> updateableColumns;

        Columns(Entity entity, JdbcConverter converter) {

            this.converter = converter;

            populateColumnNameCache(entity, "");

            Set<SqlIdentifier> insertable = new LinkedHashSet<>(nonIdColumnNames);
            insertable.removeAll(readOnlyColumnNames);

            this.insertableColumns = Collections.unmodifiableSet(insertable);

            Set<SqlIdentifier> updateable = new LinkedHashSet<>(columnNames);

            updateable.removeAll(idColumnNames);
            updateable.removeAll(readOnlyColumnNames);

            this.updateableColumns = Collections.unmodifiableSet(updateable);
        }

        private void populateColumnNameCache(Entity entity, String prefix) {

            //TODO
            EntityUtils.doWithAll(entity, field -> {
                initSimpleColumnName(field, prefix);
            });
        }

        private void initSimpleColumnName(EntityField field, String prefix) {

            SqlIdentifier columnName = SqlIdentifier.unquoted(field.getColumn());

            columnNames.add(columnName);

            if (field.getDataType() == DataType.Id) {
                idColumnNames.add(columnName);
            } else {
                nonIdColumnNames.add(columnName);
            }
        }

/*        private void initEmbeddedColumnNames(RelationalPersistentProperty property, String prefix) {

            String embeddedPrefix = property.getEmbeddedPrefix();

            RelationalPersistentEntity<?> embeddedEntity = mappingContext
                    .getRequiredPersistentEntity(converter.getColumnType(property));

            populateColumnNameCache(embeddedEntity, prefix + embeddedPrefix);
        }*/

        /**
         * @return Column names that can be used for {@code INSERT}.
         */
        Set<SqlIdentifier> getInsertableColumns() {
            return insertableColumns;
        }

        /**
         * @return Column names that can be used for {@code UPDATE}.
         */
        Set<SqlIdentifier> getUpdateableColumns() {
            return updateableColumns;
        }
    }
}
