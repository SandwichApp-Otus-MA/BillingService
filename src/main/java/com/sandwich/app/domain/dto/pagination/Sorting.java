package com.sandwich.app.domain.dto.pagination;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Sorting {

    private String parameter;
    private SortType sortType;

    public static Sorting of(String parameter, SortType sortType) {
        return new Sorting()
            .setParameter(parameter)
            .setSortType(sortType);
    }
}