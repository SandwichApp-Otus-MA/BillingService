package com.sandwich.app.domain.dto.enums;

/**
 * Тип расширенного фильтра по полю модели.
 */
public enum AdvancedFieldFilterType {

    EQUALS,

    CONTAINS,

    IN,

    NOT_IN,

    NOT_IN_OR_NULL,

    IS_NULL,

    IS_NOT_NULL,

    GREATER,

    GREATER_OR_EQUALS,

    LOWER,

    LOWER_OR_EQUALS,

    EQUALS_OR_NULL,

    EQUALS_IGNORE_CASE_OR_NULL,

    EQUALS_IGNORE_CASE,

    EQUALS_IGNORE_SPECIAL,

    BETWEEN,

    OUTSIDE,

    NOT_EQUALS,

    IN_OR_NULL,

    START_WITH,

    CONTAINS_OR_NULL,

    NOT_EQUALS_AND_NOT_NULL,

    IS_NULL_OR_LOWER_OR_EQUALS
}
