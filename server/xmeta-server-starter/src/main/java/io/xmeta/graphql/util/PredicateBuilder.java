package io.xmeta.graphql.util;

import io.xmeta.graphql.model.DateTimeFilter;
import io.xmeta.graphql.model.EnumDataTypeFilter;
import io.xmeta.graphql.model.IntFilter;
import io.xmeta.graphql.model.StringFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PredicateBuilder {


    public static List<Predicate> buildStringFilter(CriteriaBuilder criteriaBuilder, Expression<String> expression,
                                                    final StringFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getEq() != null) {
            predicates.add(equalsPredicate(criteriaBuilder, expression, filter.getEq()));
        }
        if (filter.getNot() != null) {
            predicates.add(notEqualsPredicate(criteriaBuilder, expression, filter.getNot()));
        }
        if (filter.getIn() != null) {
            predicates.add(valueInPredicate(criteriaBuilder, expression, filter.getIn()));
        }
        if (filter.getNotIn() != null) {
            predicates.add(valueNotInPredicate(criteriaBuilder, expression, filter.getNotIn()));
        }
        if (filter.getLt() != null) {
            predicates.add(lessThanPredicate(criteriaBuilder, expression, filter.getLt()));
        }
        if (filter.getLte() != null) {
            predicates.add(lessThanOrEqualToPredicate(criteriaBuilder, expression, filter.getLte()));
        }
        if (filter.getGt() != null) {
            predicates.add(greaterThanPredicate(criteriaBuilder, expression, filter.getGt()));
        }
        if (filter.getGte() != null) {
            predicates.add(greaterThanOrEqualToPredicate(criteriaBuilder, expression, filter.getGte()));
        }
        if (filter.getContains() != null) {
            predicates.add(likeUpperPredicate(criteriaBuilder, expression, filter.getContains()));
        }
        if (filter.getStartsWith() != null) {
            predicates.add(startWithPredicate(criteriaBuilder, expression, filter.getStartsWith()));
        }
        if (filter.getEndsWith() != null) {
            predicates.add(endWithPredicate(criteriaBuilder, expression, filter.getEndsWith()));
        }
        return predicates;
    }

    public static List<Predicate> buildIntFilter(CriteriaBuilder criteriaBuilder, Expression<Integer> expression,
                                                 final IntFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getEq() != null) {
            predicates.add(equalsPredicate(criteriaBuilder, expression, filter.getEq()));
        }
        if (filter.getNot() != null) {
            predicates.add(notEqualsPredicate(criteriaBuilder, expression, filter.getNot()));
        }
        if (filter.getIn() != null) {
            predicates.add(valueInPredicate(criteriaBuilder, expression, filter.getIn()));
        }
        if (filter.getNotIn() != null) {
            predicates.add(valueNotInPredicate(criteriaBuilder, expression, filter.getNotIn()));
        }
        if (filter.getLt() != null) {
            predicates.add(lessThanPredicate(criteriaBuilder, expression, filter.getLt()));
        }
        if (filter.getLte() != null) {
            predicates.add(lessThanOrEqualToPredicate(criteriaBuilder, expression, filter.getLte()));
        }
        if (filter.getGt() != null) {
            predicates.add(greaterThanPredicate(criteriaBuilder, expression, filter.getGt()));
        }
        if (filter.getGte() != null) {
            predicates.add(greaterThanOrEqualToPredicate(criteriaBuilder, expression, filter.getGte()));
        }
        return predicates;
    }

    public static List<Predicate> buildDateTimeFilter(CriteriaBuilder criteriaBuilder, Expression<ZonedDateTime> expression,
                                                      final DateTimeFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getEq() != null) {
            predicates.add(equalsPredicate(criteriaBuilder, expression, filter.getEq()));
        }
        if (filter.getNot() != null) {
            predicates.add(notEqualsPredicate(criteriaBuilder, expression, filter.getNot()));
        }
        if (filter.getIn() != null) {
            predicates.add(valueInPredicate(criteriaBuilder, expression, filter.getIn()));
        }
        if (filter.getNotIn() != null) {
            predicates.add(valueNotInPredicate(criteriaBuilder, expression, filter.getNotIn()));
        }
        if (filter.getLt() != null) {
            predicates.add(lessThanPredicate(criteriaBuilder, expression, filter.getLt()));
        }
        if (filter.getLte() != null) {
            predicates.add(lessThanOrEqualToPredicate(criteriaBuilder, expression, filter.getLte()));
        }
        if (filter.getGt() != null) {
            predicates.add(greaterThanPredicate(criteriaBuilder, expression, filter.getGt()));
        }
        if (filter.getGte() != null) {
            predicates.add(greaterThanOrEqualToPredicate(criteriaBuilder, expression, filter.getGte()));
        }
        return predicates;
    }


    public static List<Predicate> buildEnumDataTypeFilter(CriteriaBuilder criteriaBuilder, Expression<String> expression,
                                                          final EnumDataTypeFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getEq() != null) {
            predicates.add(equalsPredicate(criteriaBuilder, expression, filter.getEq().name()));
        }
        if (filter.getNot() != null) {
            predicates.add(notEqualsPredicate(criteriaBuilder, expression, filter.getNot().name()));
        }
        if (filter.getIn() != null) {
            List<String> in = new ArrayList<>();
            filter.getIn().stream().forEach(enumDataType -> {
                in.add(enumDataType.name());
            });
            predicates.add(valueInPredicate(criteriaBuilder, expression, in));
        }
        if (filter.getNotIn() != null) {
            List<String> notIn = new ArrayList<>();
            filter.getNotIn().stream().forEach(enumDataType -> {
                notIn.add(enumDataType.name());
            });
            predicates.add(valueNotInPredicate(criteriaBuilder, expression, notIn));
        }
        return predicates;
    }

    public static <X> Predicate equalsPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                final X value) {
        return criteriaBuilder.equal(expression, value);
    }

    public static <X> Predicate valueInPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                 final Collection<X> values) {
        CriteriaBuilder.In<X> in = criteriaBuilder.in(expression);
        for (X value : values) {
            in = in.value(value);
        }
        return in;
    }

    public static <X> Predicate valueNotInPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                    final Collection<X> values) {
        CriteriaBuilder.In<X> in = criteriaBuilder.in(expression);
        for (X value : values) {
            in = in.value(value);
        }
        return criteriaBuilder.not(in);
    }

    public static <X> Predicate likeUpperPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
                                                   final String value) {
        return criteriaBuilder.like(criteriaBuilder.upper(expression), wrapLikeQuery(value));
    }

    public static <X> Predicate startWithPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
                                                   final String value) {
        return criteriaBuilder.like(expression, wrapStartQuery(value));
    }

    public static <X> Predicate endWithPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
                                                 final String value) {
        return criteriaBuilder.like(expression, wrapEndQuery(value));
    }

    public static <X> Predicate doesNotContainPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
                                                        final String value) {
        return criteriaBuilder.not(criteriaBuilder.like(criteriaBuilder.upper(expression), wrapLikeQuery(value)));
    }

    public static <X> Predicate notEqualsPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                   final X value) {
        return criteriaBuilder.not(criteriaBuilder.equal(expression, value));
    }

    public static <X extends Comparable<? super X>> Predicate greaterThanPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                                                   final X value) {
        return criteriaBuilder.greaterThan(expression, value);
    }

    public static <X extends Comparable<? super X>> Predicate greaterThanOrEqualToPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                                                            final X value) {
        return criteriaBuilder.greaterThanOrEqualTo(expression, value);
    }

    public static <X extends Comparable<? super X>> Predicate lessThanPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                                                final X value) {
        return criteriaBuilder.lessThan(expression, value);
    }

    public static <X extends Comparable<? super X>> Predicate lessThanOrEqualToPredicate(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                                                         final X value) {
        return criteriaBuilder.lessThanOrEqualTo(expression, value);
    }

    public static <X> Predicate byFieldSpecified(CriteriaBuilder criteriaBuilder, Expression<X> expression,
                                                 final boolean specified) {
        return specified ? criteriaBuilder.isNotNull(expression) : criteriaBuilder.isNull(expression);
    }

    public static String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

    public static String wrapStartQuery(String txt) {
        return txt.toUpperCase() + '%';
    }

    public static String wrapEndQuery(String txt) {
        return "%" + txt.toUpperCase();
    }
}
