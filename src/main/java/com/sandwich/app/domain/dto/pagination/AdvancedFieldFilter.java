package com.sandwich.app.domain.dto.pagination;

import com.sandwich.app.domain.dto.enums.AdvancedFieldFilterType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Расширенный фильтр по полю.
 *
 * @param <T> тип значения
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdvancedFieldFilter<T> {
    private AdvancedFieldFilterType type;
    private T singleValue;
    private List<T> multipleValue;
}
