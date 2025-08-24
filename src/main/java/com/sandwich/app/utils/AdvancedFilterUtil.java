package com.sandwich.app.utils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.sandwich.app.domain.dto.pagination.AdvancedFieldFilter;
import lombok.experimental.UtilityClass;

/**
 * Утилитарный класс для работы с AdvancedFieldFilter.
 */
@UtilityClass
public class AdvancedFilterUtil {

    /**
     * Создание предиката из фильтра.
     *
     * @param expressionPath      тип сопоставления поля
     * @param advancedFieldFilter фильтр по полю
     * @param <T>                 тип поля фильтра
     * @return булево выражение в соответствии с типом фильтра
     */
    public static <T extends Comparable<?>> BooleanExpression getExpressionByAdvancedFilter(ComparableExpression<T> expressionPath,
                                                                                            AdvancedFieldFilter<T> advancedFieldFilter) {
        BooleanExpression booleanExpression = null;
        switch (advancedFieldFilter.getType()) {
            case EQUALS_OR_NULL -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.eq(advancedFieldFilter.getSingleValue())
                        .or(expressionPath.isNull());
                }
            }
            case IS_NULL -> booleanExpression = expressionPath.isNull();
            case IS_NOT_NULL -> booleanExpression = expressionPath.isNotNull();
            case EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.eq(advancedFieldFilter.getSingleValue());
                }
            }
            case NOT_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.ne(advancedFieldFilter.getSingleValue());
                }
            }
            case IS_NULL_OR_LOWER_OR_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.isNull()
                        .or(expressionPath.loe(advancedFieldFilter.getSingleValue()));
                }
            }
            case IN -> {
                if (advancedFieldFilter.getMultipleValue() != null) {
                    booleanExpression = expressionPath.in(advancedFieldFilter.getMultipleValue());
                }
            }
            case NOT_IN -> {
                if (advancedFieldFilter.getMultipleValue() != null) {
                    booleanExpression = expressionPath.notIn(advancedFieldFilter.getMultipleValue());
                }
            }
            case CONTAINS -> {
                var singleValue = advancedFieldFilter.getSingleValue();
                if (singleValue != null && singleValue.getClass() == String.class) {
                    booleanExpression = ((StringExpression) expressionPath).containsIgnoreCase((String) singleValue);
                } else {
                    throw new IllegalArgumentException("Поле \"value\" должно содержать"
                        + " значение типа \"String\"");
                }
            }
            case BETWEEN -> {
                if (advancedFieldFilter.getMultipleValue().size() == 2) {
                    booleanExpression = expressionPath
                        .between(
                            advancedFieldFilter.getMultipleValue().get(0),
                            advancedFieldFilter.getMultipleValue().get(1)
                        );
                } else {
                    throw new IllegalArgumentException("Поле \"multipleValue\" должно содержать"
                        + " ровно 2 аргумента");
                }
            }
            case GREATER_OR_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.goe(advancedFieldFilter.getSingleValue());
                }
            }
            case LOWER_OR_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.loe(advancedFieldFilter.getSingleValue());
                }
            }
            case GREATER -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.gt(advancedFieldFilter.getSingleValue());
                }
            }
            case LOWER -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = expressionPath.lt(advancedFieldFilter.getSingleValue());
                }
            }
            case EQUALS_IGNORE_CASE -> {
                var value = advancedFieldFilter.getSingleValue();
                if (value != null && value.getClass() == String.class) {
                    booleanExpression = ((StringExpression) expressionPath).equalsIgnoreCase((String) value);
                } else {
                    throw new IllegalArgumentException("Поле \"value\" должно содержать"
                        + " значение типа \"String\"");
                }
            }
            default -> throw new UnsupportedOperationException("Not implemented.");
        }

        return booleanExpression;
    }

    /**
     * Создание предиката из фильтра.
     *
     * @param numberPath          сопоставляемое поле
     * @param advancedFieldFilter фильтр по полю
     * @return булево выражение в соответствии с типом фильтра
     */
    public static Predicate getExpressionByAdvancedFilter(NumberPath<Integer> numberPath,
                                                          AdvancedFieldFilter<Integer> advancedFieldFilter) {
        BooleanExpression booleanExpression = null;
        switch (advancedFieldFilter.getType()) {
            case EQUALS_OR_NULL -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.eq(advancedFieldFilter.getSingleValue())
                        .or(numberPath.isNull());
                }
            }
            case IS_NULL -> booleanExpression = numberPath.isNull();
            case IS_NOT_NULL -> booleanExpression = numberPath.isNotNull();
            case EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.eq(advancedFieldFilter.getSingleValue());
                }
            }
            case NOT_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.ne(advancedFieldFilter.getSingleValue());
                }
            }
            case IN -> {
                if (advancedFieldFilter.getMultipleValue() != null) {
                    booleanExpression = numberPath.in(advancedFieldFilter.getMultipleValue());
                }
            }
            case NOT_IN -> {
                if (advancedFieldFilter.getMultipleValue() != null) {
                    booleanExpression = numberPath.notIn(advancedFieldFilter.getMultipleValue());
                }
            }
            case GREATER_OR_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.goe(advancedFieldFilter.getSingleValue());
                }
            }
            case LOWER_OR_EQUALS -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.loe(advancedFieldFilter.getSingleValue());
                }
            }
            case GREATER -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.gt(advancedFieldFilter.getSingleValue());
                }
            }
            case LOWER -> {
                if (advancedFieldFilter.getSingleValue() != null) {
                    booleanExpression = numberPath.lt(advancedFieldFilter.getSingleValue());
                }
            }
            default -> throw new UnsupportedOperationException("Not implemented.");
        }

        return booleanExpression;
    }
}
