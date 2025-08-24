package com.sandwich.app.domain.dto.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageData<T> {

    private List<T> content;

    private PageMetaData metaData;

}

